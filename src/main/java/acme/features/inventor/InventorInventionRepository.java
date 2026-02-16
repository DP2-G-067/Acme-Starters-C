
package acme.features.inventor;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invention.Invention;

@Repository
public interface InventorInventionRepository extends AbstractRepository {

	@Query("select i from Invention i where i.inventor.userAccount.id = :userAccountId")
	Collection<Invention> findManyByUserAccountId(int userAccountId);

	@Query("select i from Invention i where i.id = :id")
	Invention findOneById(int id);
}
