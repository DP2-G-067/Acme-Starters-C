
package acme.features.auditor.auditreport;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditReport.AuditReport;
import acme.realms.Auditor;

@Repository
public interface AuditorAuditReportRepository extends AbstractRepository {

	@Query("select a from AuditReport a where a.auditor.id = :auditorId")
	Collection<AuditReport> findManyAuditReportsByAuditorId(int auditorId);

	@Query("select a from AuditReport a where a.id = :id")
	AuditReport findOneAuditReportById(int id);

	@Query("select a from AuditReport a where a.ticker = :ticker")
	AuditReport findOneAuditReportByTicker(String ticker);

	@Query("select count(s) from AuditSection s where s.auditReport.id = :auditReportId")
	int countAuditSectionsByAuditReportId(int auditReportId);

	@Query("select a from Auditor a where a.id = :id")
	Auditor findOneAuditorById(int id);
}
