
package acme.constraints;

import java.util.Date;
import java.util.List;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.campaign.Campaign;
import acme.entities.campaign.CampaignRepository;
import acme.entities.milestone.Milestone;
import acme.entities.milestone.MilestoneRepository;

@Validator
public class CampaignValidator extends AbstractValidator<ValidCampaign, Campaign> {

	@Autowired
	private CampaignRepository	repository;

	@Autowired
	private MilestoneRepository	milestoneRepository;


	@Override
	public boolean isValid(final Campaign value, final ConstraintValidatorContext context) {
		assert context != null;
		boolean result;
		if (value == null)
			return true;
		else
			result = this.isUnique(value, context) && this.correctStatus(value, context) && this.isFutureDate(value, context);
		return result;
	}

	private boolean isUnique(final Campaign campaign, final ConstraintValidatorContext context) {
		boolean uniqueCampaign;
		Campaign existingCampaign;

		existingCampaign = this.repository.findCampaignByTicker(campaign.getTicker());
		uniqueCampaign = existingCampaign == null || existingCampaign.equals(campaign);

		super.state(context, uniqueCampaign, "ticker", "acme.validation.campaign.duplicated-ticker.message");
		return !super.hasErrors(context);
	}

	// Can not be published unless they have one milestone. 
	private boolean correctStatus(final Campaign campaign, final ConstraintValidatorContext context) {
		boolean correctStatus = true;
		List<Milestone> milestoneByCampaign = this.milestoneRepository.findAllMilestoneByCampaignId(campaign.getId());
		if (!campaign.getDraftMode())
			correctStatus = milestoneByCampaign.size() > 0;

		super.state(context, correctStatus, "draftMode", "acme.validation.campaign.draftMode.message");
		return !super.hasErrors(context);
	}

	private boolean isFutureDate(final Campaign campaign, final ConstraintValidatorContext context) {
		boolean isFutureStart = true;
		boolean isFutureEnd = true;
		Date startDate = campaign.getStartMoment();
		Date endDate = campaign.getEndMoment();

		if (!campaign.getDraftMode()) {
			isFutureStart = MomentHelper.isAfter(startDate, MomentHelper.getCurrentMoment());
			isFutureEnd = MomentHelper.isAfter(endDate, startDate);
		}
		super.state(context, isFutureStart, "startMoment", "acme.validation.campaign.startMoment.message");
		super.state(context, isFutureEnd, "endMoment", "acme.validation.campaign.endMoment.message");
		return !super.hasErrors(context);
	}
}
