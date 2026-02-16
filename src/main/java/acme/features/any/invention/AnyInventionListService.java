
package acme.features.any.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.invention.InventionRepository;

@Service
public class AnyInventionListService extends AbstractService<Any, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventionRepository		repository;

	private Collection<Invention>	inventions;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		this.inventions = this.repository.findManyPublished();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.inventions, "ticker", "name", "description");
	}
}
