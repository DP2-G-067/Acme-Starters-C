
package acme.entities.sponsorship;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.SpringHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.entities.donation.Donation;
import acme.entities.donation.DonationRepository;
import acme.realms.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsorship extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String				name;

	@Mandatory
	@ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean				draftMode;

	// Derived attributes -----------------------------------------------------


	// @Mandatory
	@Valid
	@Transient
	public Double monthsActive() {
		Double result;
		long diff;
		double months;

		if (this.startMoment == null || this.endMoment == null)
			result = null;
		else {
			diff = this.endMoment.getTime() - this.startMoment.getTime();
			months = diff / (1000.0 * 60.0 * 60.0 * 24.0 * 30.0);
			result = Math.round(months * 10.0) / 10.0;
		}

		return result;
	}

	// @ValidMoney
	@Valid
	@Transient
	public Money totalMoney() {
		Money result;
		double amount;

		DonationRepository repository = SpringHelper.getBean(DonationRepository.class);
		List<Donation> donations = repository.findBySponsorshipId(this.getId());

		amount = 0.0;
		if (donations != null)
			for (Donation donation : donations)
				if (donation.getMoney() != null && "EUR".equals(donation.getMoney().getCurrency()))
					amount += donation.getMoney().getAmount();

		result = new Money();
		result.setAmount(amount);
		result.setCurrency("EUR");

		return result;
	}

	// Relationships ----------------------------------------------------------


	@ManyToOne
	private Sponsor sponsor;

}
