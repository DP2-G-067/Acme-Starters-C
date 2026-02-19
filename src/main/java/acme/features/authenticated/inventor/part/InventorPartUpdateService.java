
package acme.features.authenticated.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.part.Part;
import acme.features.authenticated.inventor.invention.InventorPartRepository;
import acme.realms.Inventor;

@Service
public class InventorPartUpdateService extends AbstractService<Inventor, Part> {

	@Autowired
	private InventorPartRepository	repository;

	private Part					part;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.part = this.repository.findOneById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.part != null && this.part.getInvention() != null && this.part.getInvention().getInventor().isPrincipal() && Boolean.TRUE.equals(this.part.getDraftMode()) && Boolean.TRUE.equals(this.part.getInvention().getDraftMode()); // invention publicada => no editar parts

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.part, "name", "description", "kind", "cost");

		// refuerzo anti-hacking
		this.part.setInvention(this.part.getInvention());
		this.part.setDraftMode(true);
	}

	@Override
	public void validate() {
		super.validateObject(this.part);

		// EUR obligatorio
		if (this.part.getCost() != null && this.part.getCost().getCurrency() != null)
			super.state("EUR".equals(this.part.getCost().getCurrency()), "cost", "inventor.part.form.error.currency");
	}

	@Override
	public void execute() {
		this.repository.save(this.part);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.part, "name", "description", "kind", "cost", "draftMode");
		tuple.put("inventionId", this.part.getInvention().getId());
	}
}
