
package acme.entities.milestone;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import antlr.collections.List;

public interface MilestoneRepository extends AbstractRepository {

	@Query("select m from Milestone where m.campaign.id = :campaignId")
	List findAllMilestoneByCampaignId(Integer campaignId);

	@Query("select sum(m.effort) from Milestone m where m.campaign.id = :campaignId")
	Double computeCampaignEffort(Integer campaignId);
}
