
package acme.features.spokesperson.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.milestone.Milestone;
import acme.entities.milestone.MilestoneKind;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneShowService extends AbstractService<Spokesperson, Milestone> {

	@Autowired
	private SpokespersonMilestoneRepository	repository;

	private Milestone						milestone;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.milestone = this.repository.findMilestoneById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.milestone != null && this.milestone.getCampaign().getSpokesperson().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices choices = SelectChoices.from(MilestoneKind.class, this.milestone.kind);

		tuple = super.unbindObject(this.milestone, "title", "achievements", "effort", "kind", "draftMode");
		tuple.put("kind", this.milestone.getKind());
		tuple.put("choices", choices);
		tuple.put("campaignId", this.milestone.getCampaign().getId());
	}
}
