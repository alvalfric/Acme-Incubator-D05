
package acme.features.entrepeneur.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.forums.ForumUser;
import acme.entities.investmentRounds.Application;
import acme.entities.roles.Entrepeneur;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class EntrepeneurApplicationAcceptService implements AbstractUpdateService<Entrepeneur, Application> {

	@Autowired
	private EntrepeneurApplicationRepository repository;


	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;

		Application application = this.repository.findOneById(request.getModel().getInteger("id"));
		Principal principal = request.getPrincipal();

		return principal.getAccountId() == application.getInvestmentRound().getEntrepeneur().getUserAccount().getId() && application.getStatus().equals("pending");
	}

	@Override
	public void bind(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "creation", "statement", "offer", "status", "rejectJustification", "investmentRound");
	}

	@Override
	public Application findOne(final Request<Application> request) {
		assert request != null;

		Application result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}

	@Override
	public void validate(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void update(final Request<Application> request, final Application entity) {
		assert request != null;

		entity.setStatus("accepted");

		if (request.getModel().getAttribute("rejectJustification") != null) {
			entity.setRejectJustification(request.getModel().getAttribute("rejectJustification").toString());
		}

		this.repository.save(entity);

		Forum forum = this.repository.findOneForumByInvestmentRoundId(entity.getInvestmentRound().getId());
		ForumUser forumUser = new ForumUser();
		forumUser.setUser(this.repository.findOneAuthenticatedByUserAccountId(entity.getInvestor().getUserAccount().getId()));
		forumUser.setForum(forum);

		this.repository.save(forumUser);
	}

}
