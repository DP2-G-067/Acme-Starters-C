
package acme.features.auditor.auditreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.auditReport.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportShowService extends AbstractService<Auditor, AuditReport> {

	@Autowired
	protected AuditorAuditReportRepository	repository;
	private AuditReport						auditReport;


	@Override
	public void load() {
		this.auditReport = this.repository.findOneAuditReportById(super.getRequest().getData("id", int.class));
	}
	@Override
	public void authorise() {
		super.setAuthorised(this.auditReport.getAuditor().getId() == super.getRequest().getPrincipal().getActiveRealm().getId());
	}
	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}
}
