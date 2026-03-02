/*
 * JobValidator.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.constraints;

import java.util.Collection;
import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.strategy.Strategy;
import acme.entities.strategy.StrategyRepository;
import acme.entities.tactic.Tactic;

@Validator
public class StrategyValidator extends AbstractValidator<ValidStrategy, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private StrategyRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidStrategy annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Strategy strategy, final ConstraintValidatorContext context) {
		// HINT: job can be null
		assert context != null;

		boolean result;

		if (strategy == null)
			result = true;
		else {
			{
				boolean uniqueStrategy;
				Strategy existingStrategy;

				existingStrategy = this.repository.findStrategyByTicker(strategy.getTicker());
				uniqueStrategy = existingStrategy == null || existingStrategy.equals(strategy);

				super.state(context, uniqueStrategy, "ticker", "acme.validation.strategy.duplicated-ticker.message");
			}
			{
				boolean isFutureStart = true;
				boolean isFutureEnd = true;
				Date startDate = strategy.getStartMoment();
				Date endDate = strategy.getEndMoment();

				if (!strategy.getDraftMode()) {
					isFutureStart = MomentHelper.isAfter(startDate, MomentHelper.getCurrentMoment());
					isFutureEnd = MomentHelper.isAfter(endDate, startDate);
				}
				super.state(context, isFutureStart, "startMoment", "acme.validation.strategy.startMoment.message");
				super.state(context, isFutureEnd, "endMoment", "acme.validation.strategy.endMoment.message");
			}
			{
				boolean isPublishedWithNoTactics = false;
				Collection<Tactic> tactics = this.repository.findTacticsByStrategyId(strategy.getId());

				if (tactics.isEmpty() && !strategy.getDraftMode())
					isPublishedWithNoTactics = true;
				super.state(context, isPublishedWithNoTactics, "*", "acme.validation.strategy.published-with-no-tactics");
			}
			result = !super.hasErrors(context);
		}

		return result;
	}

}
