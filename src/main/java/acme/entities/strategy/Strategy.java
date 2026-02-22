
package acme.entities.strategy;

import java.time.Duration;
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
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.entities.tactic.TacticRepository;
import acme.realms.Fundraiser;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	public Date					startMoment;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	public Date					endMoment;

	@Optional
	@ValidMoment
	@Temporal(TemporalType.DATE)
	public Date					publishDate;

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
		Duration duration = MomentHelper.computeDuration(this.startMoment, this.endMoment);

		long days = duration.toDays();

		double months = days / 30.4375;

		return Math.round(months * 10.0) / 10.0;
	}

	@Transient
	public Double expectedPercentage() {
		double result;
		Double wrapper;

		wrapper = this.repository.getExpectedPercentageSum(this.getId());
		result = wrapper == null ? 0 : wrapper.doubleValue();

		return result;
	}
}
