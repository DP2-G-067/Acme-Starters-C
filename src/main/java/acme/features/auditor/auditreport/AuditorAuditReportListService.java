
package acme.features.auditor.auditreport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.auditReport.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportListService extends AbstractService<Auditor, AuditReport> {

	@Autowired
	protected AuditorAuditReportRepository	repository;

	protected Collection<AuditReport>		auditReports;


	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		int auditorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.auditReports = this.repository.findManyByAuditorId(auditorId);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditReports, "ticker", "name", "description", "startMoment", "endMoment", "draftMode");
	}
}
