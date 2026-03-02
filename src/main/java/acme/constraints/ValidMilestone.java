
package acme.constraints;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MilestoneValidator.class)
public @interface ValidMilestone {

	String message() default "";

	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
