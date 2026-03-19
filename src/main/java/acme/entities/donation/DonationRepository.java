package acme.entities.donation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface DonationRepository extends AbstractRepository {

	@Query("select count(d) > 0 from Donation d where d.sponsorship.id = :id")
	boolean existsDonationBySponsorshipId(int id);

}
