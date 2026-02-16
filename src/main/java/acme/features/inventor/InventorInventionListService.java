
package acme.features.inventor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.realms.Inventor;

@Service
public class InventorInventionListService extends AbstractService<Inventor, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorInventionRepository	repository;

	private Collection<Invention>		inventions;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.inventions = this.repository.findManyByUserAccountId(userAccountId);
	}

	@Override
	public void authorise() {
		// Si estás en realm Inventor, ya está autorizado
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		// Importante: incluye draftMode para distinguir publicadas/borrador
		super.unbindObjects(this.inventions, "ticker", "name", "draftMode");
	}
}
