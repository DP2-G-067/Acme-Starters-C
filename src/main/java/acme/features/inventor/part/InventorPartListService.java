
package acme.features.inventor.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.part.Part;
import acme.features.inventor.invention.InventorPartRepository;
import acme.realms.Inventor;

@Service
public class InventorPartListService extends AbstractService<Inventor, Part> {

	@Autowired
	private InventorPartRepository	repository;

	private Collection<Part>		parts;
	private Invention				invention;
	private int						inventionId;


	@Override
	public void load() {
		int inventionId = super.getRequest().getData("inventionId", int.class);

		this.invention = this.repository.findOneInventionById(inventionId);
		this.parts = this.repository.findManyByInventionId(inventionId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && this.invention.getInventor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {

		super.unbindObjects(this.parts, "name", "kind", "cost", "draftMode");

		Tuple tuple = new Tuple();
		tuple.put("inventionId", this.inventionId);
		super.getResponse().addGlobal("inventionId", this.inventionId);

	}
}
