package acme.entities.sponsorship;

import javax.persistence.Column;
import javax.persistence.Entity;

import acme.client.components.validation.Mandatory;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.client.components.basis.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsor extends AbstractRole {

    // Serialisation version --------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Attributes -------------------------------------------------------------

    @Mandatory
    @ValidText
    @Column
    private String address;

    @Mandatory
    @ValidHeader
    @Column
    private String im;

    @Mandatory
    @Valid
    @Column
    private boolean gold;
}
