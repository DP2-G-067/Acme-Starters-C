
package acme.features.any.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.part.Part;
import acme.entities.part.PartKind;
import acme.entities.part.PartRepository;

@Service
public class AnyPartShowService extends AbstractService<Any, Part> {

	@Autowired
	private PartRepository	repository;

	private Part			part;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.part = this.repository.findOnePartById(id);
	}

	@Override
	public void authorise() {
		boolean status = this.part != null && !this.part.getDraftMode() && !this.part.getInvention().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(PartKind.class, this.part.getKind());
		tuple = super.unbindObject(this.part, "name", "description", "cost", "kind");

		tuple.put("kinds", choices);

	}
}
