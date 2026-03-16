
package acme.features.spokesperson.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignCreateService extends AbstractService<Spokesperson, Campaign> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private SpokespersonCampaignRepository	repository;

	private Campaign						campaign;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		Spokesperson spokesperson;

		spokesperson = (Spokesperson) super.getRequest().getPrincipal().getActiveRealm();

		this.campaign = super.newObject(Campaign.class);
		this.campaign.setDescription("");
		this.campaign.setDraftMode(true);
		this.campaign.setSpokesperson(spokesperson);
	}

	@Override
	public void authorise() {
		boolean validRealm;
		validRealm = super.getRequest().getPrincipal().getActiveRealm().getClass() == Spokesperson.class;
		super.setAuthorised(validRealm);
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
	}

	@Override
	public void execute() {
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
