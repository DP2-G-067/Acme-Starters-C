
package acme.features.inventor.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.invention.Invention;
import acme.entities.part.Part;
import acme.features.inventor.invention.InventorPartRepository;
import acme.realms.Inventor;

@Service
public class InventorPartListService extends AbstractService<Inventor, Part> {

	@Autowired
	private InventorPartRepository	repository;

	private Collection<Part>		parts;
	private Invention				invention;
	private int						inventionId;


	@Override
	public void load() {
		boolean spanish;

		this.inventionId = super.getRequest().getData("inventionId", int.class);
		spanish = "es".equals(super.getRequest().getLocale().getLanguage());

		this.invention = this.repository.findOneInventionById(this.inventionId);
		this.parts = this.repository.findManyByInventionId(this.inventionId);

		for (Part part : this.parts)
			if (spanish)
				part.setStatus(Boolean.TRUE.equals(part.getDraftMode()) ? "Borrador" : "Publicado");
			else
				part.setStatus(Boolean.TRUE.equals(part.getDraftMode()) ? "Draft" : "Published");
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && this.invention.getInventor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.parts, "name", "kind", "cost", "status");

		super.getResponse().addGlobal("inventionId", this.inventionId);

		Invention invention = this.repository.findOneInventionById(this.inventionId);
		boolean showCreate = invention != null && Boolean.TRUE.equals(invention.getDraftMode());
		super.getResponse().addGlobal("showCreate", showCreate);
	}
}
