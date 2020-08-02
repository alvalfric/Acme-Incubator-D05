
package acme.features.administrator.inquirie;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.inquiries.Inquirie;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractCreateService;

@Service
public class AdministratorInquirieCreateService implements AbstractCreateService<Administrator, Inquirie> {

	@Autowired
	private AdministratorInquireRepository repository;


	@Override
	public boolean authorise(final Request<Inquirie> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Inquirie> request, final Inquirie entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creation");
	}

	@Override
	public void unbind(final Request<Inquirie> request, final Inquirie entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "creation", "deadline", "body", "minMoney", "maxMoney", "contactEmail");
	}

	@Override
	public Inquirie instantiate(final Request<Inquirie> request) {
		Inquirie result;

		result = new Inquirie();
		result.setCreation(new Date(System.currentTimeMillis() - 1));

		return result;
	}

	@Override
	public void validate(final Request<Inquirie> request, final Inquirie entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("deadline")) {
			errors.state(request, !entity.getDeadline().before(new Date(System.currentTimeMillis())), "deadline", "administrator.inquirie.error.futuro-deadline");
		}

		if (!errors.hasErrors("minMoney")) {
			errors.state(request, entity.getMinMoney().getCurrency().equals("EUR") || entity.getMinMoney().getCurrency().equals("€"), "minMoney", "administrator.inquirie.error.currency");
		}

		if (!errors.hasErrors("maxMoney")) {
			errors.state(request, entity.getMaxMoney().getCurrency().equals("EUR") || entity.getMaxMoney().getCurrency().equals("€"), "maxMoney", "administrator.inquirie.error.currency");
		}

		if (!errors.hasErrors("minMoney") && !errors.hasErrors("maxMoney")) {
			errors.state(request, entity.getMaxMoney().getAmount() >= entity.getMinMoney().getAmount(), "maxMoney", "administrator.inquirie.error.money");
		}
	}

	@Override
	public void create(final Request<Inquirie> request, final Inquirie entity) {
		Date creation;
		creation = new Date(System.currentTimeMillis() - 1);

		entity.setCreation(creation);
		this.repository.save(entity);
	}

}
