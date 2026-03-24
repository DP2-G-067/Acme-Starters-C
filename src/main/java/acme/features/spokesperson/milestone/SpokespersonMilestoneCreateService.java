
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
public class SpokespersonMilestoneCreateService extends AbstractService<Spokesperson, Milestone> {

	@Autowired
	private SpokespersonMilestoneRepository	repository;

	private Milestone						milestone;


	@Override
	public void load() {
		Campaign campaign;
		int campaignId;

		campaignId = super.getRequest().getData("campaignId", int.class);
		campaign = this.repository.findCampaignById(campaignId);

		if (campaign != null) {
			this.milestone = this.newObject(Milestone.class);
			this.milestone.setCampaign(campaign);
			this.milestone.setDraftMode(true);
		}
	}

	@Override
	public void authorise() {
		boolean status;
		Campaign campaign;
		int campaignId;

		campaignId = super.getRequest().getData("campaignId", int.class);
		campaign = this.repository.findCampaignById(campaignId);

		status = campaign != null && campaign.isDraftMode() && super.getRequest().getPrincipal().getActiveRealm().getClass() == Spokesperson.class && campaign.getSpokesperson().isPrincipal();
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
		tuple.put("campaignId", this.milestone.getCampaign().getId());
	}
}
