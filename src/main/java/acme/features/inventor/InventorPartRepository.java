
package acme.features.inventor;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.part.Part;

@Repository
public interface InventorPartRepository extends AbstractRepository {

	@Query("select p from Part p where p.invention.id = :inventionId")
	Collection<Part> findManyByInventionId(int inventionId);

	@Query("select p from Part p where p.id = :id")
	Part findOneById(int id);

	@Query("select count(p) from Part p where p.invention.id = :inventionId")
	int countByInventionId(int inventionId);

	@Query("select coalesce(sum(p.cost.amount), 0) from Part p where p.invention.id = :inventionId and p.cost.currency = 'EUR'")
	Double sumAmountEurByInventionId(int inventionId);
}
