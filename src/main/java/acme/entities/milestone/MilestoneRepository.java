
package acme.entities.milestone;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface MilestoneRepository extends AbstractRepository {

	@Query("select m from Milestone m where m.campaign.id = :campaignId")
	List<Milestone> findAllMilestoneByCampaignId(Integer campaignId);

	@Query("select sum(m.effort) from Milestone m where m.campaign.id = :campaignId")
	Double computeCampaignEffort(Integer campaignId);
}
