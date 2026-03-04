
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.part.Part;
import acme.entities.part.PartKind;
import acme.features.inventor.invention.InventorPartRepository;
import acme.realms.Inventor;

@Service
public class InventorPartCreateService extends AbstractService<Inventor, Part> {

	@Autowired
	private InventorPartRepository	repository;

	private Part					part;
	private Invention				invention;


	@Override
	public void authorise() {
		boolean status;
		int inventionId;
		Invention invention;

		inventionId = super.getRequest().getData("inventionId", int.class);
		invention = this.repository.findOneInventionById(inventionId);

		status = invention != null && invention.getInventor() != null && invention.getInventor().isPrincipal() && Boolean.TRUE.equals(invention.getDraftMode());

		this.invention = invention; // <-- CLAVE
		super.setAuthorised(status);

	}

	@Override
	public void load() {
		// Aquí ya usamos this.invention (ya validada en authorise)
		this.part = new Part();
		this.part.setInvention(this.invention);
		this.part.setDraftMode(true);
	}

	@Override
	public void bind() {
		super.bindObject(this.part, "name", "description", "kind", "cost");

		// anti-hacking
		this.part.setInvention(this.invention);
		this.part.setDraftMode(true);
	}

	@Override
	public void validate() {
		super.validateObject(this.part);

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

		tuple = super.unbindObject(this.part, "name", "description", "kind", "cost");
		tuple.put("inventionId", this.invention.getId());
		tuple.put("kinds", SelectChoices.from(PartKind.class, this.part.getKind()));
	}
}
