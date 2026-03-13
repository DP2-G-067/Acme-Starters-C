
package acme.features.spokesperson.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
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
		super.setAuthorised(true);
	}

	@Override
	public void bind() {
		int spokespersonId;

		spokespersonId = super.getRequest().getData("campaign", int.class);

		super.bindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
		this.campaign.setSpokesperson(this.repository.findSpokespersonById(spokespersonId));
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
	}

	@Override
	public void execute() {
		this.campaign.setDraftMode(false);
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
		tuple.put("monthsActive", this.campaign.getMonthsActive());
		tuple.put("effort", this.campaign.getEffort());
	}

}
