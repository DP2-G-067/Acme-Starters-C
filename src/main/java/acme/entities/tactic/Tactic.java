
package acme.entities.tactic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidScore;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.entities.strategy.Strategy;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tactic extends AbstractEntity {

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
	@ValidScore
	@Column
	public Double				expectedPercentage;

	@Mandatory
	@Valid
	@Column
	public TacticKind			kind;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	public Strategy				strategy;
}
