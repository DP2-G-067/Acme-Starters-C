
package acme.entities.campaign;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;

public interface CampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.ticker = :ticker")
	Campaign findCampaignByTicker(String ticker);

}
