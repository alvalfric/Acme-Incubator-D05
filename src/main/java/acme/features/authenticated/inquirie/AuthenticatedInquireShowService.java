
package acme.features.authenticated.inquirie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.inquiries.Inquirie;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedInquireShowService implements AbstractShowService<Authenticated, Inquirie> {

	@Autowired
	private AuthenticatedInquireRepository repository;


	@Override
	public boolean authorise(final Request<Inquirie> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Inquirie> request, final Inquirie entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		String money = entity.getMinMoney().toString() + " - " + entity.getMaxMoney().toString();
		model.setAttribute("money", money);

		request.unbind(entity, model, "title", "creation", "deadline", "body", "minMoney", "maxMoney", "contactEmail");
	}

	@Override
	public Inquirie findOne(final Request<Inquirie> request) {
		assert request != null;

		Inquirie result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneByIdActive(id);

		return result;
	}

}
