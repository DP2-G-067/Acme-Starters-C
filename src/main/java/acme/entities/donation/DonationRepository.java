package acme.entities.donation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface DonationRepository extends AbstractRepository {

	@Query(value = "select case when exists(select 1 from Donation where sponsorship_id = :id) then 1 else 0 end", nativeQuery = true)
	int existsDonationBySponsorshipId(int id);

}
