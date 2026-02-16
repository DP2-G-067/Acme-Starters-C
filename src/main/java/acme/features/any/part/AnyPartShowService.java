
package acme.features.any.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.part.Part;
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
		super.unbindObject(this.part, "name", "description", "kind", "cost");
	}
}
