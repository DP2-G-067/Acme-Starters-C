
package acme.features.authenticated.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.part.Part;
import acme.features.authenticated.inventor.invention.InventorPartRepository;
import acme.realms.Inventor;

@Service
public class InventorPartDeleteService extends AbstractService<Inventor, Part> {

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

		status = this.part != null && this.part.getInvention() != null && this.part.getInvention().getInventor().isPrincipal() && Boolean.TRUE.equals(this.part.getDraftMode()) && Boolean.TRUE.equals(this.part.getInvention().getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		; // nada que bindear para borrar
	}

	@Override
	public void validate() {
		; // sin validaciones extra
	}

	@Override
	public void execute() {
		this.repository.delete(this.part);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.part, "name", "description", "kind", "cost", "draftMode");
		tuple.put("inventionId", this.part.getInvention().getId());
	}
}
