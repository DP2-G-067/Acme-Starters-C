
package acme.entities.part;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.datatypes.Money;
import acme.client.repositories.AbstractRepository;

@Repository
public interface PartRepository extends AbstractRepository {

	//  el cost
	@Query("select count(p) from Part p where p.invention.id = :id")
	Integer countPartsByInventionId(int id);

	@Query("select coalesce(sum(p.cost.amount), 0) from Part p where p.invention.id = :id and p.cost.currency = 'EUR'")
	Money sumCostAmountEurByInventionId(int id);

	// publicadas
	@Query("select p from Part p where p.invention.id = :inventionId and p.draftMode = false")
	Collection<Part> findManyPublishedByInventionId(int inventionId);

	@Query("select p from Part p where p.id = :id")
	Part findOnePartById(int id);

	@Query("select p from Part p where p.invention.id = :inventionId")
	Collection<Part> findByInventionId(int inventionId);

}
