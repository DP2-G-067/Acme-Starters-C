
package acme.features.spokesperson.milestone;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.entities.milestone.Milestone;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneListService extends AbstractService<Spokesperson, Milestone> {

	@Autowired
	private SpokespersonMilestoneRepository	repository;

	private Campaign						campaign;
	private Collection<Milestone>			milestones;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int campaignId;

		campaignId = super.getRequest().getData("campaignId", int.class);
		this.milestones = this.repository.findMilestoneByCampaignId(campaignId);
		this.campaign = this.repository.findCampaignById(campaignId);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.campaign != null && this.campaign.getSpokesperson().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		int campaignId;

		campaignId = super.getRequest().getData("campaignId", int.class);
		super.unbindObjects(this.milestones, "title", "achievements", "effort", "kind", "draftMode");
		super.unbindGlobal("campaignId", campaignId);
		super.unbindGlobal("draftMode", this.campaign.isDraftMode());

	}
}
