
package acme.entities.tactic;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface TacticRepository extends AbstractRepository {

	@Query("select sum(t.expectedPercentage) from Tactic t where t.strategy.id = :strategyId")
	Double getExpectedPercentageSum(int strategyId);
}
