
package acme.features.any.fundraiser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategy.Strategy;
import acme.realms.Fundraiser;

@Repository
public interface AnyFundraiserRepository extends AbstractRepository {

	@Query("select s from Strategy s where s.id = :id")
	Strategy getStrategyById(int id);

	@Query("select f from Fundraiser f where f.id = :id")
	Fundraiser getFundraiserById(int id);
}
