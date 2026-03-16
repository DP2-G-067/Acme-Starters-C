
package acme.features.spokesperson.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.entities.milestone.Milestone;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignPublishService extends AbstractService<Spokesperson, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SpokespersonCampaignRepository	repository;

	private Campaign						campaign;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.campaign != null && this.campaign.isDraftMode() && this.campaign.getSpokesperson().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.campaign);

		{
			boolean milestonesByCampaign;
			int id;

			id = super.getRequest().getData("id", int.class);
			milestonesByCampaign = this.repository.countMilestoneFromCampaignId(id) > 0;
			super.state(milestonesByCampaign, "*", "acme.validation.campaign.milestones.message");
		}
		{
			boolean isValidMoment = MomentHelper.isAfter(this.campaign.getStartMoment(), MomentHelper.getCurrentMoment());
			super.state(isValidMoment, "*", "acme.validation.campaign.publish-after-start.message");
		}
		{
			boolean isValidDraft = this.campaign.isDraftMode();
			super.state(isValidDraft, "*", "acme.validation.campaign.draftMode.message");
		}
	}

	@Override
	public void execute() {
		int id;
		id = super.getRequest().getData("id", int.class);

		Collection<Milestone> milestonesByCampaign = this.repository.findMilestonesByCampaignId(id);
		milestonesByCampaign.stream().forEach(m -> m.setDraftMode(false));

		this.campaign.setDraftMode(false);
		this.repository.save(this.campaign);
		this.repository.saveAll(milestonesByCampaign);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
		tuple.put("monthsActive", this.campaign.getMonthsActive());
		tuple.put("effort", this.campaign.getEffort());
	}

}
