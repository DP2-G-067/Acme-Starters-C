
package acme.features.fundraiser.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.strategy.Strategy;
import acme.entities.tactic.Tactic;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyPublishService extends AbstractService<Fundraiser, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private FundraiserStrategyRepository	repository;

	private Strategy						strategy;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		boolean existanceAndProperty;
		boolean hasTactic;
		int id;

		id = super.getRequest().getData("id", int.class);
		existanceAndProperty = this.strategy != null && this.strategy.getDraftMode() && this.strategy.getFundraiser().isPrincipal();
		hasTactic = this.repository.countTacticsFromStrategyId(id) > 0;

		status = existanceAndProperty && hasTactic;
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.strategy);
	}

	@Override
	public void validate() {
		super.validateObject(this.strategy);
	}

	@Override
	public void execute() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.strategy.setDraftMode(false);

		Collection<Tactic> tactics = this.repository.findTacticsByStrategyId(id);
		tactics.stream().forEach(t -> t.setDraftMode(false));

		this.repository.saveAll(tactics);
		this.repository.save(this.strategy);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
		tuple.put("monthsActive", this.strategy.monthsActive());
		tuple.put("expectedPercentage", this.strategy.expectedPercentage());
	}
}
