
package acme.features.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.tactic.Tactic;
import acme.features.any.tactic.AnyTacticRepository;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticShowService extends AbstractService<Fundraiser, Tactic> {

	@Autowired
	private AnyTacticRepository	repository;

	private Tactic				tactic;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.tactic = this.repository.findTacticById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.tactic != null;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple = super.unbindObject(this.tactic, "name", "notes", "expectedPercentage");

		tuple.put("kind", this.tactic.getKind().toString());
	}
}
