
package acme.realms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.Valid;

import acme.client.components.basis.AbstractRole;
import acme.client.components.validation.Mandatory;
import acme.constraints.ValidText;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Inventor extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@ValidText
	@Column(name = "bio")
	public String				bio;

	@Mandatory
	@ValidText
	@Column(name = "keyWords")
	public String				keyWords;

	@Mandatory
	@Valid
	@Column(name = "licensed")
	public Boolean				licensed;

}
