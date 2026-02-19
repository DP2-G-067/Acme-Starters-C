
package acme.features.inventor;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.part.Part;
import acme.realms.Inventor;

@Service
public class InventorInventionPublishService extends AbstractService<Inventor, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorInventionRepository	repository;

	@Autowired
	private InventorPartRepository		partRepository;

	private Invention					invention;
	private Collection<Part>			parts;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findOneById(id);
		this.parts = this.partRepository.findManyByInventionId(id);
	}

	@Override
	public void authorise() {
		boolean status;

		// Igual que en EmployerJobPublishService: entidad existe, está en borrador y es del principal :contentReference[oaicite:1]{index=1}
		status = this.invention != null && Boolean.TRUE.equals(this.invention.getDraftMode()) && this.invention.getInventor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		// Publish se invoca como custom command de update, así que bindeamos lo editable
		// pero NO dejamos que toquen inventor ni draftMode (anti-hacking)
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");

		// Reforzar lo protegido
		this.invention.setInventor(this.invention.getInventor());
		this.invention.setDraftMode(true);
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);

		// 1) Debe tener al menos una parte
		{
			boolean hasParts;

			hasParts = this.partRepository.countByInventionId(this.invention.getId()) > 0;
			super.state(hasParts, "*", "inventor.invention.publish.error.no-parts");
		}

		// 2) Fechas futuras en el momento de publicar (requisito + formal testing)
		{
			Date now, start, end;

			now = MomentHelper.getCurrentMoment();
			start = this.invention.getStartMoment();
			end = this.invention.getEndMoment();

			if (start != null)
				super.state(start.after(now), "startMoment", "inventor.invention.publish.error.start-not-future");

			if (end != null)
				super.state(end.after(now), "endMoment", "inventor.invention.publish.error.end-not-future");

			if (start != null && end != null)
				super.state(end.after(start), "endMoment", "inventor.invention.publish.error.bad-interval");
		}

		// 3) EUR obligatorio en las parts (aunque ya lo valides en PartCreate/Update, aquí se revalida)
		{
			boolean allEur;

			allEur = this.parts != null && this.parts.stream().allMatch(p -> p.getCost() != null && "EUR".equals(p.getCost().getCurrency()));

			super.state(allEur, "*", "inventor.invention.publish.error.parts-not-eur");
		}
	}

	@Override
	public void execute() {
		// Publicar la invención
		this.invention.setDraftMode(false);
		this.repository.save(this.invention);

		// Publicar automáticamente todas sus parts
		for (Part part : this.parts) {
			part.setDraftMode(false);
			this.partRepository.save(part);
		}
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
		tuple.put("monthsActive", this.invention.getMonthsActive());
	}
}
