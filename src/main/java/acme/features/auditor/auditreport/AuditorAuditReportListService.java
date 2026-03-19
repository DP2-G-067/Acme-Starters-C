
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
	private Collection<AuditReport>			auditReports;


	@Override
	public void load() {
		this.auditReports = this.repository.findManyAuditReportsByAuditorId(super.getRequest().getPrincipal().getActiveRealm().getId());
	}
	@Override
	public void authorise() {
		super.setAuthorised(true);
	}
	@Override
	public void unbind() {
		super.unbindObjects(this.auditReports, "ticker", "name", "description", "draftMode");
	}
}
