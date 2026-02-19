
package acme.features.authenticated.inventor.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.realms.Inventor;

@Service
public class InventorInventionShowService extends AbstractService<Inventor, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorInventionRepository	repository;

	private Invention					invention;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findOneById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		// Formal testing:
		// - right realm, wrong user -> isPrincipal() lo bloquea
		status = this.invention != null && this.invention.getInventor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		boolean draftMode;

		draftMode = Boolean.TRUE.equals(this.invention.getDraftMode());

		tuple = super.unbindObject(this.invention, //
			"ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");

		// Transient calculado
		tuple.put("monthsActive", this.invention.getMonthsActive());

		// Para la UI: mostrar/ocultar botones según si está publicada
		tuple.put("showPublish", draftMode);
		tuple.put("showUpdate", draftMode);
		tuple.put("showDelete", draftMode);

		// Para navegar a parts (usamos inventionId)
		tuple.put("inventionId", this.invention.getId());
	}
}
