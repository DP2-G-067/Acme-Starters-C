
package acme.features.auditor.auditreport;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditReport.AuditReport;
import acme.entities.auditSection.AuditSection;

@Repository
public interface AuditorAuditReportRepository extends AbstractRepository {

	@Query("select r from AuditReport r where r.auditor.id = :auditorId")
	Collection<AuditReport> findManyByAuditorId(int auditorId);

	@Query("select r from AuditReport r where r.id = :id")
	AuditReport findOneAuditReportById(int id);

	@Query("select s from AuditSection s where s.auditReport.id = :auditReportId")
	Collection<AuditSection> findManyByAuditReportId(int auditReportId);
}
