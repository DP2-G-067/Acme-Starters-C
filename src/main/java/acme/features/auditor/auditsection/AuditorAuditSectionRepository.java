
package acme.features.auditor.auditsection;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditReport.AuditReport;
import acme.entities.auditSection.AuditSection;

@Repository
public interface AuditorAuditSectionRepository extends AbstractRepository {

	@Query("select s from AuditSection s where s.auditReport.id = :reportId")
	Collection<AuditSection> findManyByAuditReportId(int reportId);

	@Query("select s from AuditSection s where s.id = :id")
	AuditSection findOneAuditSectionById(int id);

	@Query("select r from AuditReport r where r.id = :id")
	AuditReport findOneAuditReportById(int id);
}
