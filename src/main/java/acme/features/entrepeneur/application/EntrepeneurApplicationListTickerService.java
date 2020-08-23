
package acme.features.entrepeneur.application;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.investmentRounds.Application;
import acme.entities.roles.Entrepeneur;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractListService;

@Service
public class EntrepeneurApplicationListTickerService implements AbstractListService<Entrepeneur, Application> {

	@Autowired
	private EntrepeneurApplicationRepository repository;


	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "creation", "offer", "status");
	}

	@Override
	public Collection<Application> findMany(final Request<Application> request) {
		assert request != null;

		Collection<Application> result;

		result = this.repository.findManyAllByEntrepeneurGroupByTicker(request.getPrincipal().getAccountId());

		return result;
	}

}
