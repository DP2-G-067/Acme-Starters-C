
package acme.entities.auditReport;

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
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidAuditReport;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.realms.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidAuditReport
public class AuditReport extends AbstractEntity {

	private static final long		serialVersionUID	= 1L;

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	public String					ticker;

	@Mandatory
	@ValidHeader
	@Column
	public String					name;

	@Mandatory
	@ValidText
	@Column
	public String					description;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	public Date						startMoment;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	public Date						endMoment;

	@Optional
	@ValidUrl
	@Column
	public String					moreInfo;

	@Mandatory
	@Valid
	@Column
	public Boolean					draftMode;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Auditor					auditor;

	@Transient
	@Autowired
	private AuditReportRepository	repository;


	@Transient
	@Mandatory

	@Valid
	public Double getMonthsActive() {
		Double result = null;
		if (this.startMoment != null && this.endMoment != null) {
			long days = ChronoUnit.DAYS.between(this.startMoment.toInstant(), this.endMoment.toInstant());
			result = Math.round(days / 30.0 * 10.0) / 10.0;
		}
		return result;
	}

	@Transient
	@Mandatory
	@ValidNumber(min = 1)
	public Integer getHours() {
		Integer result = 0;
		if (this.repository != null && this.getId() != 0) {
			result = this.repository.computeHoursByAuditReportId(this.getId());
			if (result == null)
				result = 0;
		}
		return result;
	}
}
