
package acme.features.authenticated.forum;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.forums.ForumUser;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractDeleteService;

@Service
public class AuthenticatedForumDeleteService implements AbstractDeleteService<Authenticated, Forum> {

	@Autowired
	private AuthenticatedForumRepository repository;


	@Override
	public boolean authorise(final Request<Forum> request) {
		assert request != null;

		Forum forum = this.repository.findForumById(request.getModel().getInteger("id"));
		Principal principal = request.getPrincipal();

		return forum.getInvestmentRound().getEntrepeneur().getUserAccount().getId() == principal.getAccountId();
	}

	@Override
	public void bind(final Request<Forum> request, final Forum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Forum> request, final Forum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		Forum forum = this.repository.findForumById(request.getModel().getInteger("id"));
		Principal principal = request.getPrincipal();

		model.setAttribute("canManageForum", forum.getInvestmentRound().getEntrepeneur().getUserAccount().getId() == principal.getAccountId());

		request.unbind(entity, model, "forumTitle", "forumMessages", "users");
	}

	@Override
	public Forum findOne(final Request<Forum> request) {
		assert request != null;

		Forum result;

		result = this.repository.findForumById(request.getModel().getInteger("id"));

		return result;
	}

	@Override
	public void validate(final Request<Forum> request, final Forum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<Forum> request, final Forum entity) {
		assert request != null;
		assert entity != null;

		Set<ForumUser> forumUsers = this.repository.findManyAllForumUsersByForumId(entity.getId());
		forumUsers.forEach(x -> this.repository.delete(x));
		this.repository.deleteAll(entity.getForumMessages());
		this.repository.delete(entity);
	}

}
