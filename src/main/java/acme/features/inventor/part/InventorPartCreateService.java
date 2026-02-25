
package acme.features.inventor.part;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
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
	public void load() {
		int inventionId = super.getRequest().getData("inventionId", int.class);

		this.invention = this.repository.findOneInventionById(inventionId);

		this.part = new Part();
		this.part.setInvention(this.invention);
		this.part.setDraftMode(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && this.invention.getInventor().isPrincipal() && Boolean.TRUE.equals(this.invention.getDraftMode()); // si la invention está publicada, no se pueden crear parts

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		// No bindear invention ni draftMode (anti-hacking)
		super.bindObject(this.part, "name", "description", "kind", "cost");

		// refuerzo anti-hacking
		this.part.setInvention(this.invention);
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
		tuple.put("inventionId", this.invention.getId());
		List<PartKind> kinds = Arrays.asList(PartKind.values());
		tuple.put("kinds", kinds);
	}
}
