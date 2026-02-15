
package acme.features.any.tactic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.tactic.Tactic;

public class AnyTacticListService extends AbstractService<Any, Tactic> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyTacticRepository	repository;

	private List<Tactic>		tactics;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int strategyId;

		strategyId = super.getRequest().getData("strategyId", int.class);
		this.tactics = this.repository.findTacticsByStrategyId(strategyId);
	}

	@Override
	public void authorise() {
		boolean authorised;

		authorised = !this.tactics.get(0).strategy.draftMode;

		super.setAuthorised(authorised);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.tactics, "name", "expectedPercentage", "kind");
	}

}
