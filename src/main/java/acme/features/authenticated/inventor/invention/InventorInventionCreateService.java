
package acme.features.authenticated.inventor.invention;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.realms.Inventor;

@Service
public class InventorInventionCreateService extends AbstractService<Inventor, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorInventionRepository	repository;

	private Invention					invention;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		Inventor inventor;

		inventor = (Inventor) super.getRequest().getPrincipal().getActiveRealm();

		this.invention = new Invention();
		this.invention.setInventor(inventor);
		this.invention.setDraftMode(true);		// SIEMPRE se crea en borrador (anti-hacking)
	}

	@Override
	public void authorise() {
		// Si has llegado aquí como Inventor realm, normalmente basta con true:
		super.setAuthorised(true);
	}

	@Override
	public void bind() {
		// IMPORTANTÍSIMO: no bindear inventor ni draftMode (anti-hacking)
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");

		// Reforzamos valores “protegidos” (por si alguien intenta colarlos por POST)
		Inventor inventor = (Inventor) super.getRequest().getPrincipal().getActiveRealm();
		this.invention.setInventor(inventor);
		this.invention.setDraftMode(true);
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);

		// Validación start < end + futuras, sin getBuffer()
		Date start = this.invention.getStartMoment();
		Date end = this.invention.getEndMoment();

		if (start != null && end != null)
			super.state(MomentHelper.isAfter(end, start), "endMoment", "inventor.invention.form.error.end-after-start");

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
		// Si tu JSP quiere mostrar monthsActive en create/update (readonly), puedes añadirlo:
		tuple.put("monthsActive", this.invention.getMonthsActive());
	}
}
