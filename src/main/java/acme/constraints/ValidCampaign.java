
package acme.constraints;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CampaignValidator.class)
public @interface ValidCampaign {

	String message() default "";

	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
