
package acme.features.authenticated.forum;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedForumShowService implements AbstractShowService<Authenticated, Forum> {

	@Autowired
	private AuthenticatedForumRepository repository;


	@Override
	public boolean authorise(final Request<Forum> request) {
		assert request != null;

		Forum forum = this.repository.findForumById(request.getModel().getInteger("id"));
		Authenticated auth = this.repository.findAuthenticatedByUserAccountId(request.getPrincipal().getAccountId());
		Set<Authenticated> users = this.repository.findManyAllUsersByForumId(request.getModel().getInteger("id"));

		return users.contains(auth) || forum.getInvestmentRound().getEntrepeneur().getUserAccount().getId() == auth.getUserAccount().getId();
	}

	@Override
	public void unbind(final Request<Forum> request, final Forum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		Forum forum = this.repository.findForumById(request.getModel().getInteger("id"));
		Principal principal = request.getPrincipal();

		model.setAttribute("canManageForum", forum.getInvestmentRound().getEntrepeneur().getUserAccount().getId() == principal.getAccountId());

		request.unbind(entity, model, "forumTitle", "forumMessages");
	}

	@Override
	public Forum findOne(final Request<Forum> request) {
		assert request != null;

		Forum result;

		result = this.repository.findForumById(request.getModel().getInteger("id"));

		return result;
	}

}
