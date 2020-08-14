
package acme.features.administrator.overture;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.overtures.Overture;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractCreateService;

@Service
public class AdministratorOvertureCreateService implements AbstractCreateService<Administrator, Overture> {

	@Autowired
	private AdministratorOvertureRepository repository;


	@Override
	public boolean authorise(final Request<Overture> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Overture> request, final Overture entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creation");
	}

	@Override
	public void unbind(final Request<Overture> request, final Overture entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "creation", "deadline", "body", "minMoney", "maxMoney", "contactEmail");
	}

	@Override
	public Overture instantiate(final Request<Overture> request) {
		Overture result;

		result = new Overture();
		result.setCreation(new Date(System.currentTimeMillis() - 1));

		return result;
	}

	@Override
	public void validate(final Request<Overture> request, final Overture entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("deadline")) {
			if (entity.getDeadline() == null) {
				errors.state(request, entity.getDeadline() != null, "deadline", "overture.requests.error.null");
			} else {
				errors.state(request, !entity.getDeadline().before(new Date(System.currentTimeMillis())), "deadline", "administrator.overture.error.futuro-deadline");
			}
		}

		if (!errors.hasErrors("minMoney")) {
			errors.state(request, entity.getMinMoney().getCurrency().equals("EUR") || entity.getMinMoney().getCurrency().equals("€"), "minMoney", "administrator.overture.error.currency");
		}

		if (!errors.hasErrors("maxMoney")) {
			errors.state(request, entity.getMaxMoney().getCurrency().equals("EUR") || entity.getMaxMoney().getCurrency().equals("€"), "maxMoney", "administrator.overture.error.currency");
		}

		if (!errors.hasErrors("minMoney") && !errors.hasErrors("maxMoney")) {
			errors.state(request, entity.getMaxMoney().getAmount() >= entity.getMinMoney().getAmount(), "maxMoney", "administrator.overture.error.money");
		}
	}

	@Override
	public void create(final Request<Overture> request, final Overture entity) {
		Date creation;
		creation = new Date(System.currentTimeMillis() - 1);

		entity.setCreation(creation);
		this.repository.save(entity);
	}

}
