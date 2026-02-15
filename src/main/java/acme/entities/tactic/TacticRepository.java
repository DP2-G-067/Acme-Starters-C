
package acme.entities.tactic;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface TacticRepository extends AbstractRepository {

	@Query("select * from Tactic t where t.strategy.id = :strategyId")
	List<Tactic> getTacticsByStrategyId(int strategyId);
}
