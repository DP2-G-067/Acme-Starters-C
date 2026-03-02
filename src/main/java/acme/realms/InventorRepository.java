
package acme.realms;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface InventorRepository extends AbstractRepository {

	@Query("select i from Inventor i where i.id = :id")
	Inventor findOneInventorById(int id);
}
