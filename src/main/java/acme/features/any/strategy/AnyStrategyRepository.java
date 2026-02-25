
package acme.features.any.strategy;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategy.Strategy;

@Repository
public interface AnyStrategyRepository extends AbstractRepository {

	@Query("select s from Strategy s where draftMode = false")
	List<Strategy> findPublishedStrategies();

	@Query("select s from Strategy s where s.id = :id")
	Strategy findStrategyById(int id);
}
