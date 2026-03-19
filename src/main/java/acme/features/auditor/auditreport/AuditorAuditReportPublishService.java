
package acme.features.auditor.auditreport;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.auditReport.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportPublishService extends AbstractService<Auditor, AuditReport> {

	@Autowired
	protected AuditorAuditReportRepository	repository;
	private AuditReport						auditReport;


	@Override
	public void load() {
		this.auditReport = this.repository.findOneAuditReportById(super.getRequest().getData("id", int.class));
	}
	@Override
	public void authorise() {
		boolean isOwner = this.auditReport.getAuditor().getId() == super.getRequest().getPrincipal().getActiveRealm().getId();
		super.setAuthorised(isOwner && this.auditReport.getDraftMode());
	}
	@Override
	public void bind() {
		super.bindObject(this.auditReport, "ticker", "name", "startMoment", "endMoment", "description", "moreInfo");
	}
	@Override
	public void validate() {
		super.validateObject(this.auditReport);
		if (!super.getErrors().hasErrors("ticker")) {
			AuditReport existing = this.repository.findOneAuditReportByTicker(this.auditReport.getTicker());
			super.state(existing == null || existing.getId() == this.auditReport.getId(), "ticker", "acme.validation.audit-report.duplicated-ticker.message");
		}
		if (!super.getErrors().hasErrors("startMoment") && !super.getErrors().hasErrors("endMoment"))
			super.state(!this.auditReport.getEndMoment().before(this.auditReport.getStartMoment()), "endMoment", "acme.validation.audit-report.noTimeCompliant.message");

		int sectionsCount = this.repository.countAuditSectionsByAuditReportId(this.auditReport.getId());
		super.state(sectionsCount > 0, "*", "acme.validation.audit-report.draftMode.message");

		Date now = new Date();
		super.state(this.auditReport.getStartMoment() == null || !this.auditReport.getStartMoment().before(now), "startMoment", "acme.validation.audit-report.publish-after-start.message");
	}
	@Override
	public void execute() {
		this.auditReport.setDraftMode(false);
		this.repository.save(this.auditReport);
	}
	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "startMoment", "endMoment", "description", "moreInfo", "draftMode");
	}
}
