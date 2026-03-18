
package acme.entities.auditReport;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditReportRepository extends AbstractRepository {

	@Query("select sum(s.hours) from AuditSection s where s.auditReport.id = :id")
	Integer computeHoursByAuditReportId(int id);

	@Query("select a from AuditReport a where a.ticker = :ticker")
	AuditReport findAuditReportByTicker(String ticker);

	@Query("select count(s) from AuditSection s where s.auditReport.id = :id")
	Integer countSectionsByAuditReportId(int id);
}
