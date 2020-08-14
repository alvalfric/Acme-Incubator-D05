
package acme.features.authenticated.forumUser;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.forums.ForumUser;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Authenticated;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedForumUserAddService implements AbstractCreateService<Authenticated, ForumUser> {

	@Autowired
	private AuthenticatedForumUserRepository repository;


	@Override
	public boolean authorise(final Request<ForumUser> request) {
		assert request != null;

		Forum forum = this.repository.findForumById(request.getModel().getInteger("forumId"));

		return forum.getInvestmentRound().getEntrepeneur().getUserAccount().getId() == request.getPrincipal().getAccountId();
	}

	@Override
	public void bind(final Request<ForumUser> request, final ForumUser entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.getModel().setAttribute("users", this.repository.findManyAllUsersByForumId(request.getModel().getInteger("forumId")));
		request.getModel().setAttribute("cantDeleteUserId", request.getPrincipal().getAccountId());

		request.bind(entity, errors, "addUserByUsername");
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
	public ForumUser instantiate(final Request<ForumUser> request) {
		assert request != null;

		ForumUser result = new ForumUser();
		result.setForum(this.repository.findForumById(request.getModel().getInteger("forumId")));
		Authenticated dummyUser = this.repository.findAuthenticatedByUsername(request.getPrincipal().getUsername());
		result.setUser(dummyUser);

		return result;
	}

	@Override
	public void validate(final Request<ForumUser> request, final ForumUser entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Set<Authenticated> users = this.repository.findManyAllUsersByForumId(request.getModel().getInteger("forumId"));
		Set<Authenticated> allUsers = this.repository.findManyAllAuthenticated();
		String username = request.getModel().getAttribute("addUserByUsername").toString();

		if (!errors.hasErrors("users") && username != null) {
			if (allUsers.stream().anyMatch(x -> x.getUserAccount().getUsername().equals(username))) {
				boolean userAlreadyInForum = users.stream().anyMatch(x -> x.getUserAccount().getUsername().equals(username));
				errors.state(request, !userAlreadyInForum, "addUserByUsername", "authenticated.forum-user.error.alredy-in-forum");
			} else {
				errors.state(request, false, "addUserByUsername", "authenticated.forum-user.error.not-found");
			}
		}

	}

	@Override
	public void create(final Request<ForumUser> request, final ForumUser entity) {
		assert request != null;
		assert entity != null;
		Set<Authenticated> users = this.repository.findManyAllUsersByForumId(request.getModel().getInteger("forumId"));
		String username = request.getModel().getAttribute("addUserByUsername").toString();

		if (username != null) {
			Authenticated addedUser = this.repository.findAuthenticatedByUsername(username);
			if (!users.stream().anyMatch(x -> x.getUserAccount().getUsername().equals(username))) {
				ForumUser forumUser = new ForumUser();
				forumUser.setUser(addedUser);
				forumUser.setForum(this.repository.findForumById(request.getModel().getInteger("forumId")));
				this.repository.save(forumUser);
			}
		}

	}

	@Override
	public void onSuccess(final Request<ForumUser> request, final Response<ForumUser> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

}
