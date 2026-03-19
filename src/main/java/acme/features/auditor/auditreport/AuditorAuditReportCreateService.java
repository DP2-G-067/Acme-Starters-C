
package acme.features.auditor.auditreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.auditReport.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportCreateService extends AbstractService<Auditor, AuditReport> {

	@Autowired
	protected AuditorAuditReportRepository	repository;
	private AuditReport						auditReport;


	@Override
	public void load() {
		this.auditReport = new AuditReport();
		this.auditReport.setDraftMode(true);
		this.auditReport.setAuditor(this.repository.findOneAuditorById(super.getRequest().getPrincipal().getActiveRealm().getId()));
	}
	@Override
	public void authorise() {
		super.setAuthorised(true);
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
			super.state(existing == null, "ticker", "acme.validation.audit-report.duplicated-ticker.message");
		}
		if (!super.getErrors().hasErrors("startMoment") && !super.getErrors().hasErrors("endMoment"))
			super.state(!this.auditReport.getEndMoment().before(this.auditReport.getStartMoment()), "endMoment", "acme.validation.audit-report.noTimeCompliant.message");
	}
	@Override
	public void execute() {
		this.repository.save(this.auditReport);
	}
	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "startMoment", "endMoment", "description", "moreInfo", "draftMode");
	}
}
