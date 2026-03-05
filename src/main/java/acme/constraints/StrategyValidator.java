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

	@Autowired
	private StrategyRepository repository;


	@Override
	public boolean isValid(final Strategy value, final ConstraintValidatorContext context) {
		assert context != null;
		boolean result;
		if (value == null)
			return true;
		else
			result = this.isUnique(value, context) && this.correctStatus(value, context) && this.isFutureDate(value, context);
		return result;
	}

	private boolean isUnique(final Strategy strategy, final ConstraintValidatorContext context) {
		boolean uniqueStrategy;
		Strategy existingStrategy;

		existingStrategy = this.repository.findStrategyByTicker(strategy.getTicker());
		uniqueStrategy = existingStrategy == null || existingStrategy.equals(strategy);

		super.state(context, uniqueStrategy, "ticker", "acme.validation.strategy.duplicated-ticker.message");
		return !super.hasErrors(context);
	}

	// Can not be published unless they have one milestone. 
	private boolean correctStatus(final Strategy strategy, final ConstraintValidatorContext context) {
		boolean correctStatus = true;
		Collection<Tactic> tacticByStrategy = this.repository.findTacticsByStrategyId(strategy.getId());
		if (!strategy.getDraftMode())
			correctStatus = tacticByStrategy.size() > 0;

		super.state(context, correctStatus, "draftMode", "acme.validation.strategy.draftMode.message");
		return !super.hasErrors(context);
	}

	private boolean isFutureDate(final Strategy strategy, final ConstraintValidatorContext context) {
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
		return !super.hasErrors(context);
	}
}
