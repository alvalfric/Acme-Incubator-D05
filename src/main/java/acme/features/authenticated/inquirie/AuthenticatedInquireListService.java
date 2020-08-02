
package acme.features.authenticated.inquirie;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.inquiries.Inquirie;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedInquireListService implements AbstractListService<Authenticated, Inquirie> {

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

		request.unbind(entity, model, "title", "creation", "deadline");
	}

	@Override
	public Collection<Inquirie> findMany(final Request<Inquirie> request) {
		assert request != null;

		Collection<Inquirie> result;

		result = this.repository.findManyAllActive();

		return result;
	}

}
