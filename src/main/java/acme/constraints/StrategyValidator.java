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

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.strategy.Strategy;
import acme.entities.strategy.StrategyRepository;

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
			result = this.isUnique(value, context) && this.correctStatus(value, context) && this.isTimeCompliant(value, context);
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

	private boolean correctStatus(final Strategy strategy, final ConstraintValidatorContext context) {
		boolean isValid = strategy.getDraftMode() || !strategy.getDraftMode() && this.repository.existsTacticsFromStrategyId(strategy.getId());

		super.state(context, isValid, "*", "acme.validation.strategy.draftMode.message");
		return !super.hasErrors(context);
	}

	private boolean isTimeCompliant(final Strategy strategy, final ConstraintValidatorContext context) {
		if (strategy.getStartMoment() == null || strategy.getEndMoment() == null)
			return true;

		Date start = strategy.getStartMoment();
		Date end = strategy.getEndMoment();

		super.state(context, MomentHelper.isAfter(end, start), "*", "acme.validation.strategy.noTimeCompliant.message");

		return !super.hasErrors(context);
	}
}
