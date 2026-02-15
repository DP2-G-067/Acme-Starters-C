
package acme.features.any.fundraiser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.realms.Fundraiser;

@Repository
public interface AnyFundraiserRepository extends AbstractRepository {

	@Query("select s.fundraiser from Strategy s where s.id = :id")
	Fundraiser getFundraiserByStrategyId(int id);
}
