
package acme.features.auditor.auditsection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.auditSection.AuditSection;
import acme.entities.auditSection.SectionKind;
import acme.realms.Auditor;

@Service
public class AuditorAuditSectionShowService extends AbstractService<Auditor, AuditSection> {

	@Autowired
	protected AuditorAuditSectionRepository	repository;
	private AuditSection					auditSection;


	@Override
	public void load() {
		this.auditSection = this.repository.findOneAuditSectionById(super.getRequest().getData("id", int.class));
	}
	@Override
	public void authorise() {
		super.setAuthorised(this.auditSection.getAuditReport().getAuditor().getId() == super.getRequest().getPrincipal().getActiveRealm().getId());
	}
	@Override
	public void unbind() {
		super.unbindGlobal("draftMode", this.auditSection.getAuditReport().getDraftMode());
		super.unbindGlobal("kinds", SelectChoices.from(SectionKind.class, this.auditSection.getKind()));
		super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");
	}
}
