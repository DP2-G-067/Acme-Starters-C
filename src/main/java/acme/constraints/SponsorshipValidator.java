
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.donation.DonationRepository;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.sponsorship.SponsorshipRepository;

@Validator
public class SponsorshipValidator extends AbstractValidator<ValidSponsorship, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	@Autowired
	private DonationRepository		donationRepository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidSponsorship annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Sponsorship sponsorship, final ConstraintValidatorContext context) {
		assert context != null;

		if (sponsorship == null)
			return true;

		this.checkTickerIsUnique(sponsorship, context);
		this.checkTimeCompliance(sponsorship, context);
		this.checkPublicationConsistency(sponsorship, context);

		return !super.hasErrors(context);
	}

	private void checkTickerIsUnique(final Sponsorship sponsorship, final ConstraintValidatorContext context) {
		boolean uniqueTicker;
		Sponsorship existingSponsorship;

		existingSponsorship = this.sponsorshipRepository.findSponsorshipByTicker(sponsorship.getTicker());
		uniqueTicker = existingSponsorship == null || existingSponsorship.equals(sponsorship);

		super.state(context, uniqueTicker, "ticker", "acme.validation.sponsorship.duplicated-ticker.message");
	}

	private void checkTimeCompliance(final Sponsorship sponsorship, final ConstraintValidatorContext context) {
		Date start = sponsorship.getStartMoment();
		Date end = sponsorship.getEndMoment();

		if (start != null && end != null)
			super.state(context, MomentHelper.isAfter(end, start), "*", "acme.validation.sponsorship.noTimeCompliant.message");
	}

	private void checkPublicationConsistency(final Sponsorship sponsorship, final ConstraintValidatorContext context) {
		boolean isPublishedWithNoDonations = sponsorship.getDraftMode() != null && !sponsorship.getDraftMode() //
			&& !this.donationRepository.existsDonationBySponsorshipId(sponsorship.getId());

		super.state(context, !isPublishedWithNoDonations, "*", "acme.validation.sponsorship.draftMode.message");
	}

}
