
package acme.features.spokesperson.campaign;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaign.Campaign;
import acme.entities.milestone.Milestone;
import acme.realms.Spokesperson;

public interface SpokespersonCampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);

	@Query("select c from Campaign c where c.spokesperson.id = :spokespersonId")
	Collection<Campaign> findCampaignBySpokespersonId(int spokespersonId);

	@Query("select count(m)>0 from Milestone m where m.campaign.id = :id")
	Boolean countMilestoneFromCampaignId(int id);

	@Query("select m from Milestone m where m.campaign.id = :id")
	Collection<Milestone> findMilestonesByCampaignId(int id);

	@Query("select s from Spokesperson s where s.id = :id")
	Spokesperson findSpokespersonById(int id);

}
