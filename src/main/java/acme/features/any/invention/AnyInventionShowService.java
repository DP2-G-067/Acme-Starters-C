
package acme.features.any.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.datatypes.Money;
import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.invention.InventionRepository;
import acme.entities.part.PartRepository;

@Service
public class AnyInventionShowService extends AbstractService<Any, Invention> {

	@Autowired
	private InventionRepository	repository;

	@Autowired
	private PartRepository		partRepository;

	private Invention			invention;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findOneInventionById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && !this.invention.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		Money cost = this.computePublishedCost(this.invention.getId());

		tuple = super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive");
		tuple.put("inventorId", this.invention.getInventor().getId());
		tuple.put("cost", cost);

	}

	// ---- helper ----

	private Money computePublishedCost(final int inventionId) {
		Collection<Money> costs = this.partRepository.findPublishedCostsByInventionId(inventionId);

		double total = 0.0;
		if (costs != null)
			for (Money m : costs)
				if (m != null)
					// asumimos EUR (y además tu validador ya lo fuerza)
					total += m.getAmount();

		Money result = new Money();
		result.setCurrency("EUR");
		result.setAmount(total);
		return result;
	}
}
