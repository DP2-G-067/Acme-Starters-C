
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
public class FundraiserTacticUpdateService extends AbstractService<Fundraiser, Tactic> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private FundraiserTacticRepository	repository;

	private Strategy					strategy;
	private Tactic						tactic;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.tactic = this.repository.findTacticById(id);
		if (this.tactic != null)
			this.strategy = this.tactic.getStrategy();
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.strategy != null && this.strategy.getDraftMode() && this.strategy.getFundraiser().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.tactic);
	}

	@Override
	public void execute() {
		this.repository.save(this.tactic);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices choices = SelectChoices.from(TacticKind.class, this.tactic.kind);

		tuple = super.unbindObject(this.tactic, "name", "notes", "expectedPercentage", "kind", "draftMode");
		tuple.put("kind", this.tactic.getKind());
		tuple.put("choices", choices);
	}
}
