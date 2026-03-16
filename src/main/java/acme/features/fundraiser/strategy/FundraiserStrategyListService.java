
package acme.features.fundraiser.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.strategy.Strategy;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyListService extends AbstractService<Fundraiser, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private FundraiserStrategyRepository	repository;

	private Collection<Strategy>			strategies;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().getActiveRealm().getClass() == Fundraiser.class;
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int fundraiserId;

		fundraiserId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.strategies = this.repository.findStrategyByFundraiserId(fundraiserId);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.strategies, "ticker", "name", "description", "startMoment");
	}

}
