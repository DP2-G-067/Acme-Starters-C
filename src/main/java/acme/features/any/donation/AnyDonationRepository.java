package acme.features.any.donation;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.donation.Donation;
import acme.entities.sponsorship.Sponsorship;

@Repository
public interface AnyDonationRepository extends AbstractRepository {

	@Query("select d from Donation d where d.sponsorship.id = :id and d.sponsorship.draftMode = false")
	Collection<Donation> findDonationsBySponsorshipId(int id);

	@Query("select d from Donation d where d.id = :id and d.sponsorship.draftMode = false")
	Donation findDonationById(int id);

	@Query("select s from Sponsorship s where s.id = :id and s.draftMode = false")
	Sponsorship findSponsorshipById(int id);
}
