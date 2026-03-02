
package acme.entities.part;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.datatypes.Money;
import acme.client.repositories.AbstractRepository;

@Repository
public interface PartRepository extends AbstractRepository {

	@Query("select count(p) from Part p where p.invention.id = :id")
	Integer countPartsByInventionId(int id);

	@Query("select coalesce(sum(p.cost.amount), 0) from Part p where p.invention.id = :id and p.cost.currency = 'EUR'")
	Money sumCostAmountEurByInventionId(int id);
}
