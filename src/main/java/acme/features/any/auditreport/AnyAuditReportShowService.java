
package acme.features.any.auditreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.auditReport.AuditReport;

@Service
public class AnyAuditReportShowService extends AbstractService<Any, AuditReport> {

	@Autowired
	protected AnyAuditReportRepository	repository;

	private AuditReport					auditReport;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findAuditReportById(id);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		Tuple tuple = super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
		tuple.put("hours", this.auditReport.getHours());
		tuple.put("auditorId", this.auditReport.getAuditor().getId());
	}
}
