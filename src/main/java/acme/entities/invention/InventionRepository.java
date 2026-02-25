
package acme.entities.invention;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface InventionRepository extends AbstractRepository {

	@Query("select i from Invention i where i.id = :id")
	Invention findOneInventionById(int id);

	@Query("select i from Invention i where i.draftMode = false")
	Collection<Invention> findManyPublished();

	@Query("select i from Invention i where i.ticker = :ticker")
	Invention findInventionByTicker(String ticker);

}
