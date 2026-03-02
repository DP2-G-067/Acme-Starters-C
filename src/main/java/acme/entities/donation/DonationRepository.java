package acme.entities.donation;

import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface DonationRepository extends AbstractRepository {

    boolean existsBySponsorshipId(int id);
}
