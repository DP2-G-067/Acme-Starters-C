
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
	private Collection<AuditSection>		auditSections;
	private AuditReport						auditReport;


	@Override
	public void load() {
		int auditReportId = super.getRequest().getData("auditReportId", int.class);
		this.auditReport = this.repository.findOneAuditReportById(auditReportId);
		this.auditSections = this.repository.findManyAuditSectionsByAuditReportId(auditReportId);
	}
	@Override
	public void authorise() {
		super.setAuthorised(this.auditReport.getAuditor().getId() == super.getRequest().getPrincipal().getActiveRealm().getId());
	}
	@Override
	public void unbind() {
		super.unbindGlobal("draftMode", this.auditReport.getDraftMode());
		super.unbindGlobal("auditReportId", this.auditReport.getId());
		super.unbindObjects(this.auditSections, "name", "hours", "kind");
	}
}
