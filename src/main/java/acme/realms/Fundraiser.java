
package acme.realms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.Valid;

import acme.client.components.basis.AbstractRole;
import acme.client.components.validation.Mandatory;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Fundraiser extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@ValidHeader
	@Column(name = "bank")
	public String				bank;

	@Mandatory
	@ValidText
	@Column(name = "statement")
	public String				statement;

	@Mandatory
	@Valid
	@Column(name = "agent")
	public Boolean				agent;
}
