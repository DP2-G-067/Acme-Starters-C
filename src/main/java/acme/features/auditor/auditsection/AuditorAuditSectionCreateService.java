
package acme.features.auditor.auditsection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.auditReport.AuditReport;
import acme.entities.auditSection.AuditSection;
import acme.entities.auditSection.SectionKind;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionCreateService extends AbstractService<Auditor, AuditSection> {

	@Autowired
	protected AuditorAuditSectionRepository	repository;

	protected AuditSection					auditSection;
	protected AuditReport					auditReport;


	@Override
	public void authorise() {
		boolean status = this.auditReport != null && this.auditReport.getAuditor().getId() == super.getRequest().getPrincipal().getActiveRealm().getId() && this.auditReport.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int auditReportId = super.getRequest().getData("auditReportId", int.class);
		this.auditReport = this.repository.findOneAuditReportById(auditReportId);

		this.auditSection = super.newObject(AuditSection.class);
		this.auditSection.setAuditReport(this.auditReport);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditSection, "name", "notes", "hours", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditSection);
	}

	@Override
	public void execute() {
		this.repository.save(this.auditSection);
	}

	@Override
	public void unbind() {
		super.unbindGlobal("auditReportId", this.auditReport.getId());
		super.unbindGlobal("kindChoices", SelectChoices.from(SectionKind.class, this.auditSection.getKind()));
		super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");
	}
}
