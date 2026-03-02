
package acme.features.any.campaign;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaign.Campaign;

public interface AnyCampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.draftMode = false")
	Collection<Campaign> findAllPublishedCampaigns();

	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);
}
