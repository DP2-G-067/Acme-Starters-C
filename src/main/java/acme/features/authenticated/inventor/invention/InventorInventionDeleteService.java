
package acme.features.authenticated.inventor.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.part.Part;
import acme.realms.Inventor;

@Service
public class InventorInventionDeleteService extends AbstractService<Inventor, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorInventionRepository	repository;

	@Autowired
	private InventorPartRepository		partRepository;

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
		// - right user, wrong action -> draftMode==true requerido
		status = this.invention != null && Boolean.TRUE.equals(this.invention.getDraftMode()) && this.invention.getInventor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		// Igual que en JobDeleteService: bindear campos del form por consistencia,
		// pero NO permitir cambiar inventor/draftMode por hacking
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
		this.invention.setInventor(this.invention.getInventor());
		this.invention.setDraftMode(true);
	}

	@Override
	public void validate() {
		// No hay validaciones adicionales para borrar (como en JobDeleteService) :contentReference[oaicite:2]{index=2}
		;
	}

	@Override
	public void execute() {
		Collection<Part> parts;

		// Borrar primero los hijos (parts), luego el padre (invention)
		parts = this.partRepository.findManyByInventionId(this.invention.getId());
		this.partRepository.deleteAll(parts);
		this.repository.delete(this.invention);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
		tuple.put("monthsActive", this.invention.getMonthsActive());
	}
}
