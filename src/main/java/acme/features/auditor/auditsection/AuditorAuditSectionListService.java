
package acme.features.auditor.auditsection;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.auditReport.AuditReport;
import acme.entities.auditSection.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionListService extends AbstractService<Auditor, AuditSection> {

	@Autowired
	protected AuditorAuditSectionRepository	repository;

	protected Collection<AuditSection>		auditSections;


	@Override
	public void authorise() {
		int auditReportId = super.getRequest().getData("auditReportId", int.class);
		AuditReport auditReport = this.repository.findOneAuditReportById(auditReportId);
		boolean status = auditReport != null && auditReport.getAuditor().getId() == super.getRequest().getPrincipal().getActiveRealm().getId();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int auditReportId = super.getRequest().getData("auditReportId", int.class);
		this.auditSections = this.repository.findManyByAuditReportId(auditReportId);
	}

	@Override
	public void unbind() {
		int auditReportId = super.getRequest().getData("auditReportId", int.class);
		AuditReport auditReport = this.repository.findOneAuditReportById(auditReportId);

		super.unbindGlobal("auditReportId", auditReportId);
		super.unbindGlobal("draftMode", auditReport.getDraftMode());
		super.unbindObjects(this.auditSections, "name", "notes", "hours", "kind");
	}
}
