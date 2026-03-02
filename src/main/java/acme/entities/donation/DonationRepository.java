package acme.entities.donation;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface DonationRepository extends AbstractRepository {

    @Query("select d from Donation d where d.sponsorship.id = :id")
    List<Donation> findBySponsorshipId(int id);
}
