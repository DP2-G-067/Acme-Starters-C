
package acme.features.spokesperson.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.entities.milestone.Milestone;
import acme.entities.milestone.MilestoneKind;
import acme.realms.Fundraiser;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneCreateService extends AbstractService<Spokesperson, Milestone> {

	@Autowired
	private SpokespersonMilestoneRepository	repository;

	private Campaign						campaign;
	private Milestone						milestone;


	@Override
	public void load() {
		int campaignId = super.getRequest().getData("campaignId", int.class);

		this.campaign = this.repository.findCampaignById(campaignId);

		this.milestone = this.newObject(Milestone.class);
		this.milestone.setCampaign(this.campaign);
		this.milestone.setDraftMode(true);
	}

	@Override
	public void authorise() {
		super.setAuthorised(super.getRequest().getPrincipal().getActiveRealm().getClass() == Fundraiser.class);
	}

	@Override
	public void bind() {
		super.bindObject(this.milestone, "title", "achievements", "effort", "kind", "draftMode");
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
		tuple.put("campaignId", this.milestone.getCampaign().getId());
	}
}
