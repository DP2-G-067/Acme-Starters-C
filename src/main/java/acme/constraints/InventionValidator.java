
package acme.constraints;

import java.util.Collection;
import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.datatypes.Money;
import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.invention.Invention;
import acme.entities.invention.InventionRepository;
import acme.entities.part.Part;
import acme.entities.part.PartRepository;

@Validator
public class InventionValidator extends AbstractValidator<ValidInvention, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventionRepository	inventionRepository;

	@Autowired
	private PartRepository		partRepository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidInvention annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Invention invention, final ConstraintValidatorContext context) {
		assert context != null;

		if (invention == null)
			return true;

		this.checkTickerIsUnique(invention, context);
		this.checkTimeInterval(invention, context);
		this.checkPublishingRequiresParts(invention, context);
		this.checkPartsCostCurrencyIsEuro(invention, context);

		return !super.hasErrors(context);
	}

	// Business rules ---------------------------------------------------------

	private void checkTickerIsUnique(final Invention invention, final ConstraintValidatorContext context) {
		boolean unique;
		Invention existing;

		existing = this.inventionRepository.findInventionByTicker(invention.getTicker());
		unique = existing == null || existing.equals(invention);

		super.state(context, unique, "ticker", "acme.entities.invention.error.ticker.not-unique");
	}

	private void checkTimeInterval(final Invention invention, final ConstraintValidatorContext context) {
		boolean ok = false;

		final Date start = invention.getStartMoment();
		final Date end = invention.getEndMoment();

		/*
		 * Regla: start/end deben ser un intervalo válido en el futuro
		 * respecto al momento en el que se publica.
		 *
		 * Interpretación práctica:
		 * - Si está en draft: no forzamos "futuro respecto a ahora" (porque aún no está publicada),
		 * pero sí exigimos end > start (intervalo válido).
		 * - Si NO está en draft (publicada): exigimos start y end en el futuro respecto a "ahora" y end > start.
		 *
		 * Esto encaja con la especificación del modelo. :contentReference[oaicite:1]{index=1}
		 */

		if (start != null && end != null) {
			final boolean endAfterStart = MomentHelper.isAfter(end, start);

			final Boolean draftMode = invention.getDraftMode();
			final boolean isDraft = draftMode != null && draftMode;

			if (isDraft)
				ok = endAfterStart;
			else {
				final Date now = MomentHelper.getCurrentMoment();
				final boolean startFuture = MomentHelper.isAfter(start, now);
				final boolean endFuture = MomentHelper.isAfter(end, now);
				ok = endAfterStart && startFuture && endFuture;
			}
		}

		super.state(context, ok, "*", "acme.entities.invention.error.not-time-compliant");
	}

	private void checkPublishingRequiresParts(final Invention invention, final ConstraintValidatorContext context) {
		boolean ok = true;

		final Boolean draftMode = invention.getDraftMode();
		final boolean isPublished = draftMode != null && !draftMode;

		if (isPublished) {
			final Collection<Part> parts = this.partRepository.findByInventionId(invention.getId());
			ok = parts != null && !parts.isEmpty();
		}

		// “Inventions cannot be published unless they have at least one part.” :contentReference[oaicite:2]{index=2}
		super.state(context, ok, "*", "acme.entities.invention.error.published-without-parts");
	}

	private void checkPartsCostCurrencyIsEuro(final Invention invention, final ConstraintValidatorContext context) {
		boolean ok = true;

		// Solo tiene sentido si ya existe en BD (id != 0); si es nueva, aún no tendrá parts persistidas.
		if (invention.getId() != 0) {
			final Collection<Part> parts = this.partRepository.findByInventionId(invention.getId());

			if (parts != null)
				for (final Part p : parts) {
					final Money cost = p.getCost();
					if (cost != null) {
						final String currency = cost.getCurrency();
						if (currency == null || !"EUR".equals(currency)) {
							ok = false;
							break;
						}
					}
				}
		}

		// “Only Euros are accepted.” :contentReference[oaicite:3]{index=3}
		super.state(context, ok, "*", "acme.entities.invention.error.parts-cost-not-eur");
	}
}
