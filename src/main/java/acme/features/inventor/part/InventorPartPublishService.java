
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
	InventorPartRepository	repository;
	private Part			part;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.part = this.repository.findOneById(id);
	}

	@Override
	public void authorise() {
		boolean status = this.part != null && this.part.getInvention().getInventor().isPrincipal() && Boolean.TRUE.equals(this.part.getDraftMode());
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		// normalmente no hace falta bindear nada para publicar
	}

	@Override
	public void validate() {
		// si tienes reglas extra, aquí
	}

	@Override
	public void unbind() {
		Tuple tuple = super.unbindObject(this.part, "name", "description", "cost", "kind", "draftMode");
		super.getResponse().setData(tuple); // si tu versión NO lo tiene, elimínalo (como antes)
	}
}
