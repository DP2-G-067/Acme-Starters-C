
package acme.features.inventor.invention;

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
		boolean spanish;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		spanish = "es".equals(super.getRequest().getLocale().getLanguage());

		this.inventions = this.repository.findManyByUserAccountId(userAccountId);

		for (Invention invention : this.inventions)
			if (spanish)
				invention.setStatus(Boolean.TRUE.equals(invention.getDraftMode()) ? "Borrador" : "Publicado");
			else
				invention.setStatus(Boolean.TRUE.equals(invention.getDraftMode()) ? "Draft" : "Published");
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.inventions, "ticker", "name", "status");
	}
}
