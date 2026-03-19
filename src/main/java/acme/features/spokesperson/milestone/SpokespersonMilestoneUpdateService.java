
package acme.features.spokesperson.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.entities.milestone.Milestone;
import acme.entities.milestone.MilestoneKind;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneUpdateService extends AbstractService<Spokesperson, Milestone> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SpokespersonMilestoneRepository	repository;

	private Milestone						milestone;
	private Campaign						campaign;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.milestone = this.repository.findMilestoneById(id);
		if (this.milestone != null)
			this.campaign = this.milestone.getCampaign();
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.campaign != null && this.campaign.isDraftMode() && this.campaign.getSpokesperson().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.milestone, "title", "achievements", "effort", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.milestone);
	}

	@Override
	public void execute() {
		this.repository.save(this.milestone);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices choices = SelectChoices.from(MilestoneKind.class, this.milestone.kind);

		tuple = super.unbindObject(this.milestone, "title", "achievements", "effort", "kind", "draftMode");
		tuple.put("kind", this.milestone.getKind());
		tuple.put("choices", choices);
	}
}
