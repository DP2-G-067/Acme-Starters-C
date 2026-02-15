
package acme.features.any.tactic;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.tactic.Tactic;

@Repository
public interface AnyTacticRepository extends AbstractRepository {

	@Query("select t from Tactic t where t.strategy.id = :id")
	List<Tactic> findTacticsByStrategyId(int id);

	@Query("select t from Tactic t where t.id = :id")
	Tactic findTacticById(int id);
}
