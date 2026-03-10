
package acme.features.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.tactic.Tactic;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticDeleteService extends AbstractService<Fundraiser, Tactic> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private FundraiserTacticRepository	repository;

	private Tactic						tactic;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.tactic = this.repository.findTacticById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.tactic != null && this.tactic.getDraftMode() && this.tactic.getStrategy().getFundraiser().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.tactic);
	}

	@Override
	public void validate() {
		super.validateObject(this.tactic);
	}

	@Override
	public void execute() {
		this.repository.delete(this.tactic);
	}
}
