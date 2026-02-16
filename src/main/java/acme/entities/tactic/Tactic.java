
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
	@Column(name = "name")
	public String				name;

	@Mandatory
	@ValidText
	@Column(name = "notes")
	public String				notes;

	@Mandatory
	@ValidScore
	@Column(name = "expectedPercentage")
	public Double				expectedPercentage;

	@Mandatory
	@Valid
	@Column(name = "kind")
	public TacticKind			kind;

	@ManyToOne
	public Strategy				strategy;
}
