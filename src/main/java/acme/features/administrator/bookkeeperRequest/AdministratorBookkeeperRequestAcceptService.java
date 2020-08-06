/*
 * AuthenticatedConsumerUpdateService.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.bookkeeperRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Bookkeeper;
import acme.entities.roles.BookkeeperRequest;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Administrator;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractUpdateService;

@Service
public class AdministratorBookkeeperRequestAcceptService implements AbstractUpdateService<Administrator, BookkeeperRequest> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorBookkeeperRequestRepository repository;


	// AbstractUpdateService<Authenticated, Consumer> interface -----------------

	@Override
	public boolean authorise(final Request<BookkeeperRequest> request) {
		assert request != null;

		return true;
	}

	@Override
	public void validate(final Request<BookkeeperRequest> request, final BookkeeperRequest entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void bind(final Request<BookkeeperRequest> request, final BookkeeperRequest entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<BookkeeperRequest> request, final BookkeeperRequest entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("username", this.repository.findOneUserAccountByBookkeeperRequestId(request.getModel().getInteger("id")).getUsername());

		request.unbind(entity, model, "firmName", "responsabilityStatement");
	}

	@Override
	public BookkeeperRequest findOne(final Request<BookkeeperRequest> request) {
		assert request != null;

		BookkeeperRequest result;

		result = this.repository.findOneBookkeeperRequestById(request.getModel().getInteger("id"));

		return result;
	}

	@Override
	public void update(final Request<BookkeeperRequest> request, final BookkeeperRequest entity) {
		assert request != null;
		assert entity != null;

		Bookkeeper bookkeeper = new Bookkeeper();
		bookkeeper.setFirmName(entity.getFirmName());
		bookkeeper.setResponsabilityStatement(entity.getResponsabilityStatement());
		bookkeeper.setUserAccount(entity.getUserAccount());

		this.repository.save(bookkeeper);
		this.repository.delete(entity);
	}

	@Override
	public void onSuccess(final Request<BookkeeperRequest> request, final Response<BookkeeperRequest> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

}
