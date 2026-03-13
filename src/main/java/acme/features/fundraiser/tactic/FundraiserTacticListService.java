
package acme.features.fundraiser.tactic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.strategy.Strategy;
import acme.entities.tactic.Tactic;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticListService extends AbstractService<Fundraiser, Tactic> {

	@Autowired
	private FundraiserTacticRepository	repository;

	private Strategy					strategy;
	private Collection<Tactic>			tactics;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int strategyId;

		strategyId = super.getRequest().getData("strategyId", int.class);
		this.tactics = this.repository.findTacticsByStrategyId(strategyId);
		this.strategy = this.repository.findStrategyById(strategyId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		int strategyId;

		strategyId = super.getRequest().getData("strategyId", int.class);
		super.unbindObjects(this.tactics, "name", "expectedPercentage", "kind");
		super.unbindGlobal("strategyId", strategyId);
		super.unbindGlobal("draftMode", this.strategy.getDraftMode());

	}
}
