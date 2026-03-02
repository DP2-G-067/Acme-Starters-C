
package acme.features.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.strategy.Strategy;
import acme.entities.tactic.Tactic;
import acme.realms.Fundraiser;

public class FundraiserTacticCreateService extends AbstractService<Fundraiser, Tactic> {

	@Autowired
	private FundraiserTacticRepository	repository;

	private Strategy					strategy;
	private Tactic						tactic;


	@Override
	public void load() {
		int strategyId = super.getRequest().getData("strategyId", int.class);

		this.strategy = this.repository.findStrategyById(strategyId);

		this.tactic = new Tactic();
		this.tactic.setStrategy(this.strategy);
		this.tactic.setDraftMode(true);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
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
		SelectChoices choices;
		//TODO: añadir las choices para el enum kind

		tuple = super.unbindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
	}
}
