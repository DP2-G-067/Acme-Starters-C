
package acme.features.any.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.invention.InventionRepository;
import acme.entities.part.Part;
import acme.entities.part.PartRepository;

@Service
public class AnyPartListService extends AbstractService<Any, Part> {

	@Autowired
	private PartRepository		partRepository;

	@Autowired
	private InventionRepository	inventionRepository;

	private Collection<Part>	parts;
	private Invention			invention;


	@Override
	public void load() {
		int inventionId = super.getRequest().getData("inventionId", int.class);
		this.invention = this.inventionRepository.findOneInventionById(inventionId);
		this.parts = this.partRepository.findManyPublishedByInventionId(inventionId);
	}

	@Override
	public void authorise() {
		boolean status = this.invention != null && !this.invention.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.parts, "name", "kind", "cost");
	}
}
