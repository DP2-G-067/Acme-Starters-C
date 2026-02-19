
package acme.entities.part;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoney;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.entities.invention.Invention;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Part extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@ValidHeader
	@Column
	public String				name;

	@Mandatory
	@ValidText
	@Column
	public String				description;

	@Mandatory
	@ValidMoney(min = 0)
	@Column
	public Money				cost;

	@Mandatory
	@Valid
	@Column
	public PartKind				kind;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	public Invention			invention;

	@Mandatory
	@Valid
	@Column
	private Boolean				draftMode;

}
