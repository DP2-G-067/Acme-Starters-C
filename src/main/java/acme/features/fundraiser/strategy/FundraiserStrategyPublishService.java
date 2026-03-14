
package acme.features.fundraiser.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MomentHelper;
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

		status = this.strategy != null && this.strategy.getDraftMode() && this.strategy.getFundraiser().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		boolean isValidMoment = MomentHelper.isAfter(this.strategy.getStartMoment(), MomentHelper.getCurrentMoment());
		super.state(isValidMoment, "*", "acme.validation.strategy.publish-after-start.message");

		boolean isValidState = this.strategy.getDraftMode() && this.repository.existsTacticsFromStrategyId(this.strategy.getId());
		super.state(isValidState, "*", "acme.validation.strategy.draftMode.message");
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
