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

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.auditReport.AuditReport;
import acme.entities.auditReport.AuditReportRepository;

@Validator
public class AuditReportValidator extends AbstractValidator<ValidAuditReport, AuditReport> {

	@Autowired
	private AuditReportRepository repository;


	@Override
	protected void initialise(final ValidAuditReport annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final AuditReport auditReport, final ConstraintValidatorContext context) {
		assert context != null;
		boolean result;

		if (auditReport == null)
			result = true;
		else {
			boolean uniqueTicker;
			AuditReport existingAuditReport;

			existingAuditReport = this.repository.findAuditReportByTicker(auditReport.getTicker());
			uniqueTicker = existingAuditReport == null || existingAuditReport.equals(auditReport);

			super.state(context, uniqueTicker, "ticker", "acme.validation.auditReport.duplicated-ticker.message");

			boolean hasSections;
			Integer sectionsCount = this.repository.countSectionsByAuditReportId(auditReport.getId());
			hasSections = auditReport.getDraftMode() || sectionsCount != null && sectionsCount >= 1;

			super.state(context, hasSections, "*", "acme.validation.auditReport.no-sections.message");

			boolean validInterval;
			if (auditReport.getDraftMode() || auditReport.getStartMoment() == null || auditReport.getEndMoment() == null)
				validInterval = true;
			else
				validInterval = MomentHelper.isFuture(auditReport.getStartMoment()) && auditReport.getEndMoment().after(auditReport.getStartMoment());

			super.state(context, validInterval, "*", "acme.validation.auditReport.invalid-interval.message");

			result = !super.hasErrors(context);
		}

		return result;
	}
}
