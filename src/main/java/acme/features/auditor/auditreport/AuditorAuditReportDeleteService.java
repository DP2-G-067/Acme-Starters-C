
package acme.features.auditor.auditreport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.auditReport.AuditReport;
import acme.entities.auditSection.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportDeleteService extends AbstractService<Auditor, AuditReport> {

	@Autowired
	protected AuditorAuditReportRepository	repository;

	protected AuditReport					auditReport;


	@Override
	public void authorise() {
		boolean status = this.auditReport != null && this.auditReport.getAuditor().getId() == super.getRequest().getPrincipal().getActiveRealm().getId() && this.auditReport.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findOneAuditReportById(id);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditReport);
	}

	@Override
	public void execute() {
		Collection<AuditSection> sections = this.repository.findManyByAuditReportId(this.auditReport.getId());

		for (AuditSection section : sections)
			this.repository.delete(section);

		this.repository.delete(this.auditReport);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}
}
