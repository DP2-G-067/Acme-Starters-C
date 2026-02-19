
package acme.features.inventor;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.realms.Inventor;

@Service
public class InventorInventionUpdateService extends AbstractService<Inventor, Invention> {

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
		// - right user, wrong action -> solo si está en borrador
		status = this.invention != null && Boolean.TRUE.equals(this.invention.getDraftMode()) && this.invention.getInventor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		// No bindear inventor ni draftMode (anti-hacking)
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");

		// Reforzar atributos protegidos
		this.invention.setInventor(this.invention.getInventor());
		this.invention.setDraftMode(true);
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);

		Date start = this.invention.getStartMoment();
		Date end = this.invention.getEndMoment();

		// end > start
		if (start != null && end != null)
			super.state(end.after(start), "endMoment", "inventor.invention.form.error.end-after-start");

		// futuras (en update también deben seguir siendo futuras)
		// OJO: en publish también se revalida con el "now" del reloj del sistema
		if (start != null)
			super.state(MomentHelper.isFuture(start), "startMoment", "inventor.invention.form.error.start-future");
		if (end != null)
			super.state(MomentHelper.isFuture(end), "endMoment", "inventor.invention.form.error.end-future");
	}

	@Override
	public void execute() {
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
		tuple.put("monthsActive", this.invention.getMonthsActive());

		// flags para la UI
		tuple.put("showPublish", Boolean.TRUE.equals(this.invention.getDraftMode()));
		tuple.put("showDelete", Boolean.TRUE.equals(this.invention.getDraftMode()));
		tuple.put("inventionId", this.invention.getId());
	}
}
