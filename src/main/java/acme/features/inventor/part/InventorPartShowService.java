
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.part.Part;
import acme.entities.part.PartKind;
import acme.features.inventor.invention.InventorPartRepository;
import acme.realms.Inventor;

@Service
public class InventorPartShowService extends AbstractService<Inventor, Part> {

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

		status = this.part != null && this.part.getInvention() != null && this.part.getInvention().getInventor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		boolean draftMode;

		draftMode = Boolean.TRUE.equals(this.part.getDraftMode());

		tuple = super.unbindObject(this.part, "name", "description", "kind", "cost", "draftMode");
		tuple.put("inventionId", this.part.getInvention().getId());

		// choices del enum
		tuple.put("kinds", SelectChoices.from(PartKind.class, this.part.getKind()));

		// botones
		tuple.put("showUpdate", draftMode);
		tuple.put("showPublish", draftMode);
		tuple.put("showDelete", draftMode);
	}
}
