
package acme.features.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.strategy.Strategy;
import acme.entities.tactic.Tactic;
import acme.entities.tactic.TacticKind;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticCreateService extends AbstractService<Fundraiser, Tactic> {

	@Autowired
	private FundraiserTacticRepository	repository;

	private Strategy					strategy;
	private Tactic						tactic;


	@Override
	public void load() {
		int strategyId = super.getRequest().getData("strategyId", int.class);

		this.strategy = this.repository.findStrategyById(strategyId);

		this.tactic = this.newObject(Tactic.class);
		this.tactic.setStrategy(this.strategy);
		this.tactic.setDraftMode(true);
	}

	@Override
	public void authorise() {
		super.setAuthorised(super.getRequest().getPrincipal().getActiveRealm().getClass() == Fundraiser.class);
	}

	@Override
	public void bind() {
		super.bindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
	}

	@Override
	public void validate() {
	}

	@Override
	public void execute() {
		this.repository.save(this.tactic);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices choices = SelectChoices.from(TacticKind.class, this.tactic.kind);

		tuple = super.unbindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
		tuple.put("kind", this.tactic.getKind());
		tuple.put("choices", choices);
		tuple.put("strategyId", this.tactic.getStrategy().getId());
	}
}
