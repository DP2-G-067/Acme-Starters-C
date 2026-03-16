
package acme.entities.strategy;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidStrategy;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.entities.tactic.TacticRepository;
import acme.realms.Fundraiser;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidStrategy
public class Strategy extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	public String				ticker;

	@Mandatory
	@ValidHeader
	@Column
	public String				name;

	@Mandatory
	@ValidText
	@Column
	public String				description;

	@Mandatory
	@ValidMoment()
	@Temporal(TemporalType.TIMESTAMP)
	public Date					startMoment;

	@Mandatory
	@ValidMoment()
	@Temporal(TemporalType.TIMESTAMP)
	public Date					endMoment;

	@Optional
	@ValidUrl
	@Column
	public String				moreInfo;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	public Fundraiser			fundraiser;

	@Mandatory
	@Valid
	@Column
	public Boolean				draftMode;

	@Transient
	@Autowired
	private TacticRepository	repository;


	@Valid
	@Transient
	public Double monthsActive() {
		if (this.getStartMoment() == null || this.getEndMoment() == null)
			return 0.;

		return MomentHelper.computeDifference(this.startMoment, this.endMoment, ChronoUnit.MONTHS);
	}

	@Transient
	public Double expectedPercentage() {
		double result;
		Double wrapper;

		if (this.isTransient())
			return 0.;

		wrapper = this.repository.getExpectedPercentageSum(this.getId());
		result = wrapper == null ? 0 : wrapper.doubleValue();

		return result;
	}
}
