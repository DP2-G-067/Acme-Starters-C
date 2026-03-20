
package acme.features.authenticated.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.realms.Auditor;

@Service
public class AuthenticatedAuditorShowService extends AbstractService<Authenticated, Auditor> {

	@Autowired
	protected AuthenticatedAuditorRepository	repository;
	private Auditor								auditor;


	@Override
	public void load() {
		this.auditor = this.repository.findOneAuditorByUserAccountId(super.getRequest().getPrincipal().getAccountId());
	}
	@Override
	public void authorise() {
		super.setAuthorised(this.repository.findOneAuditorByUserAccountId(super.getRequest().getPrincipal().getAccountId()) != null);
	}
	@Override
	public void unbind() {
		super.unbindObject(this.auditor, "firm", "highlights", "solicitor");
	}
}
