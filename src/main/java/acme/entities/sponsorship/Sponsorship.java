package acme.entities.sponsorship;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsorship extends AbstractEntity {

    // Serialisation version --------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Attributes -------------------------------------------------------------

    @Mandatory
    @ValidTicker
    @Column(unique = true)
    private String ticker;

    @Mandatory
    @ValidHeader
    @Column
    private String name;

    @Mandatory
    @ValidText
    @Column
    private String description;

    @Mandatory
    @ValidMoment
    @Temporal(TemporalType.TIMESTAMP)
    private Date startMoment;

    @Mandatory
    @ValidMoment
    @Temporal(TemporalType.TIMESTAMP)
    private Date endMoment;

    @Optional
    @ValidUrl
    @Column
    private String moreInfo;

    @Mandatory
    @Valid
    @Column
    private boolean draftMode;

    // Derived attributes -----------------------------------------------------

    @Mandatory
    @Valid
    @Transient
    public Double getMonthsActive() {
        Double result;
        long diff;
        double months;

        if (this.startMoment == null || this.endMoment == null) {
            result = null;
        } else {
            diff = this.endMoment.getTime() - this.startMoment.getTime();
            months = diff / (1000.0 * 60.0 * 60.0 * 24.0 * 30.0);
            result = Math.round(months * 10.0) / 10.0;
        }

        return result;
    }

    @Mandatory
    @ValidMoney
    @Transient
    public Money getTotalMoney() {
        Money result;
        double amount = 0.0;

        if (this.donations != null) {
            for (Donation donation : this.donations) {
                if (donation.getMoney() != null && "EUR".equals(donation.getMoney().getCurrency())) {
                    amount += donation.getMoney().getAmount();
                }
            }
        }

        result = new Money();
        result.setAmount(amount);
        result.setCurrency("EUR");

        return result;
    }

    // Relationships ----------------------------------------------------------

    @Mandatory
    @Valid
    @ManyToOne
    private Sponsor sponsor;

    @Valid
    @OneToMany
    @JoinColumn(name = "sponsorship_id")
    private List<Donation> donations;

    // Complex validations ----------------------------------------------------

    @AssertTrue(message = "Un Sponsorship no puede publicarse (salir de draftMode) si no tiene al menos una Donation asociada")
    public boolean isDraftModeConsistent() {
        return this.draftMode || (this.donations != null && !this.donations.isEmpty());
    }

    @AssertTrue(message = "startMoment debe ser anterior a endMoment")
    public boolean isPeriodConsistent() {
        return this.startMoment == null || this.endMoment == null || this.startMoment.before(this.endMoment);
    }

    @AssertTrue(message = "startMoment y endMoment deben ser futuros")
    public boolean isFutureConsistent() {
        return (this.startMoment == null || MomentHelper.isFuture(this.startMoment)) &&
                (this.endMoment == null || MomentHelper.isFuture(this.endMoment));
    }

}
