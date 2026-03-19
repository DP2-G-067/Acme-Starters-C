
package acme.entities.auditSection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidNumber;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.entities.auditReport.AuditReport;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditSection extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@ValidHeader
	@Column
	public String				name;

	@Mandatory
	@ValidText
	@Column
	public String				notes;

	@Mandatory
	@ValidNumber(min = 1)
	@Column
	public Integer				hours;

	@Mandatory
	@Valid
	@Column
	public SectionKind			kind;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	public AuditReport			auditReport;
}
