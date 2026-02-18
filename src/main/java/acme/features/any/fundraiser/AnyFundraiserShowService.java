
package acme.features.any.fundraiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategy.Strategy;
import acme.realms.Fundraiser;

@Service
public class AnyFundraiserShowService extends AbstractService<Any, Fundraiser> {

	@Autowired
	private AnyFundraiserRepository	repository;

	private Strategy				strategy;
	private Fundraiser				fundraiser;


	@Override
	public void load() {
		int strategyId;

		strategyId = super.getRequest().getData("strategyId", int.class);
		this.strategy = this.repository.getStrategyById(strategyId);
		this.fundraiser = this.repository.getFundraiserById(this.strategy.getFundraiser().getId());
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.strategy != null && !this.strategy.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.fundraiser, "bank", "statement", "agent");
	}
}
