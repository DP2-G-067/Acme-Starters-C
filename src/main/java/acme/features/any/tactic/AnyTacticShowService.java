
package acme.features.any.tactic;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.tactic.Tactic;

public class AnyTacticShowService extends AbstractService<Any, Tactic> {

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

		status = this.tactic != null && !this.tactic.strategy.draftMode;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
	}
}
