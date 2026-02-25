
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.datatypes.Money;
import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.invention.Invention;
import acme.entities.part.Part;
import acme.entities.part.PartRepository;

@Validator
public class PartValidator extends AbstractValidator<ValidPart, Part> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private PartRepository partRepository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidPart annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Part part, final ConstraintValidatorContext context) {
		assert context != null;

		if (part == null)
			return true;

		this.checkMoneyCurrencyIsEuro(part, context);
		this.checkPublishingConsistency(part, context);

		return !super.hasErrors(context);
	}

	// Business rules ---------------------------------------------------------

	private void checkMoneyCurrencyIsEuro(final Part part, final ConstraintValidatorContext context) {
		boolean ok = true;

		final Money cost = part.getCost();
		if (cost != null) {
			final String currency = cost.getCurrency();
			ok = currency != null && "EUR".equals(currency);
		}

		super.state(context, ok, "cost", "acme.entities.part.error.cost-not-eur");
	}

	private void checkPublishingConsistency(final Part part, final ConstraintValidatorContext context) {
		boolean ok = true;

		final Boolean draftMode = part.getDraftMode();
		final boolean isPublished = draftMode != null && !draftMode;

		if (isPublished) {
			// La Part no debería poder publicarse si su Invention está en draft
			final Invention invention = part.getInvention();
			if (invention == null || invention.getId() == 0)
				ok = false;
			else {
				// refrescamos desde BD para evitar que venga “medio cargada”
				final Part persisted = this.partRepository.findOnePartById(part.getId());
				// si es nueva (id=0) no aplica; si ya existe, usamos su invention real
				final Invention inv = persisted != null ? persisted.getInvention() : invention;

				ok = inv != null && inv.getDraftMode() != null && !inv.getDraftMode();
			}
		}

		super.state(context, ok, "*", "acme.entities.part.error.published-with-invention-draft");
	}
}
