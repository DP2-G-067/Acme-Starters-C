
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.part.Part;
import acme.features.inventor.invention.InventorPartRepository;
import acme.realms.Inventor;

@Service
public class InventorPartPublishService extends AbstractService<Inventor, Part> {

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

		// Existe, es del inventor principal y está en borrador
		status = this.part != null && this.part.getInvention() != null && this.part.getInvention().getInventor().isPrincipal() && Boolean.TRUE.equals(this.part.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		; // no binding on publish
	}

	@Override
	public void validate() {
		// Validación estándar de la entidad (por si tienes ValidMoney, etc.)
		super.validateObject(this.part);

		// Reglas extra (opcionales pero recomendables)
		super.state(this.part.getCost() != null, "cost", "inventor.part.publish.error.no-cost");
		if (this.part.getCost() != null) {
			super.state(this.part.getCost().getAmount() > 0.0, "cost", "inventor.part.publish.error.non-positive");
			super.state("EUR".equals(this.part.getCost().getCurrency()), "cost", "inventor.part.publish.error.not-eur");
		}
	}

	@Override
	public void execute() {
		this.part.setDraftMode(false);
		this.repository.save(this.part);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.part, "name", "description", "kind", "cost", "draftMode");
		tuple.put("inventionId", this.part.getInvention().getId());
		tuple.put("kinds", acme.client.components.views.SelectChoices.from(acme.entities.part.PartKind.class, this.part.getKind()));
	}
}
