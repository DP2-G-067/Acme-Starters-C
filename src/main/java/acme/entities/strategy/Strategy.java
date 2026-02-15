
package acme.entities.strategy;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import org.springframework.data.annotation.Transient;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.SpringHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.entities.tactic.Tactic;
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
	@Column(name = "name")
	public String				name;

	@Mandatory
	@ValidText
	@Column(name = "description")
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
	@ValidUrl
	@Column(name = "moreInfo")
	public String				moreInfo;

	@Valid
	@ManyToOne
	public Fundraiser			fundraiser;


	@Valid
	@Transient
	public Double monthsActive() {

		if (this.startMoment == null || this.endMoment == null)
			return null;

		LocalDate start = this.startMoment.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		LocalDate end = this.endMoment.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		if (end.isBefore(start))
			return 0.0;

		long days = ChronoUnit.DAYS.between(start, end);

		double months = days / 30.4375;

		return Math.round(months * 10.0) / 10.0;
	}

	@Valid
	@Transient
	public Double expectedPercentage() {

		TacticRepository repository = SpringHelper.getBean(TacticRepository.class);
		List<Tactic> tactics = repository.getTacticsByStrategyId(this.getId());

		if (tactics == null || tactics.isEmpty())
			return 0.0;

		return tactics.stream().map(Tactic::getExpectedPercentage).filter(p -> p != null).reduce(0.0, Double::sum);
	}
}
