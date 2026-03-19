
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

	protected AuditSection					auditSection;


	@Override
	public void authorise() {
		boolean status = this.auditSection != null && this.auditSection.getAuditReport().getAuditor().getId() == super.getRequest().getPrincipal().getActiveRealm().getId();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.auditSection = this.repository.findOneAuditSectionById(id);
	}

	@Override
	public void unbind() {
		super.unbindGlobal("draftMode", this.auditSection.getAuditReport().getDraftMode());
		super.unbindGlobal("kindChoices", SelectChoices.from(SectionKind.class, this.auditSection.getKind()));
		super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");
	}
}
