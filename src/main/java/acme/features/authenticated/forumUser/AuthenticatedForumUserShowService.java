
package acme.features.authenticated.forumUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.forums.ForumUser;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedForumUserShowService implements AbstractShowService<Authenticated, ForumUser> {

	@Autowired
	private AuthenticatedForumUserRepository repository;


	@Override
	public boolean authorise(final Request<ForumUser> request) {
		assert request != null;

		Forum forum = this.repository.findForumById(request.getModel().getInteger("forumId"));

		return forum.getInvestmentRound().getEntrepeneur().getUserAccount().getId() == request.getPrincipal().getAccountId();
	}

	@Override
	public void unbind(final Request<ForumUser> request, final ForumUser entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("forumId", request.getModel().getInteger("forumId"));
		model.setAttribute("users", this.repository.findManyAllUsersByForumId(request.getModel().getInteger("forumId")));
		model.setAttribute("cantDeleteUserId", request.getPrincipal().getAccountId());

		request.unbind(entity, model);
	}

	@Override
	public ForumUser findOne(final Request<ForumUser> request) {
		assert request != null;

		ForumUser result;

		result = this.repository.findForumUserByUserAccountIdAndForumId(request.getPrincipal().getAccountId(), request.getModel().getInteger("forumId"));

		return result;
	}

}
