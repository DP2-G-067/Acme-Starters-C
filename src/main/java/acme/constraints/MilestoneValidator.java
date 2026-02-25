
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.milestone.Milestone;

@Validator
public class MilestoneValidator extends AbstractValidator<ValidMilestone, Milestone> {

	@Override
	public boolean isValid(final Milestone value, final ConstraintValidatorContext context) {
		assert context != null;
		boolean result;
		if (value == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		result = !super.hasErrors(context);
		return result;
	}

}
