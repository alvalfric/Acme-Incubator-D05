
package acme.features.authenticated.forumMessage;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.forums.ForumMessage;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedForumCreateService implements AbstractCreateService<Authenticated, ForumMessage> {

	@Autowired
	private AuthenticatedForumMessageRepository repository;


	@Override
	public boolean authorise(final Request<ForumMessage> request) {
		assert request != null;

		Forum forum = this.repository.findForumById(request.getModel().getInteger("forumId"));
		Authenticated auth = this.repository.findAuthenticatedByUserAccountId(request.getPrincipal().getAccountId());

		return forum.getUsers().contains(auth);
	}

	@Override
	public void bind(final Request<ForumMessage> request, final ForumMessage entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creation");
	}

	@Override
	public void unbind(final Request<ForumMessage> request, final ForumMessage entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("forumId", request.getModel().getInteger("forumId"));

		request.unbind(entity, model, "title", "tags", "body", "creation");

		if (request.isMethod(HttpMethod.GET)) {
			model.setAttribute("confirmation", "false");
		} else {
			request.transfer(model, "confirmation");
		}
	}

	@Override
	public ForumMessage instantiate(final Request<ForumMessage> request) {
		assert request != null;

		Forum forum = this.repository.findForumById(request.getModel().getInteger("forumId"));
		Authenticated auth = this.repository.findAuthenticatedByUserAccountId(request.getPrincipal().getAccountId());

		ForumMessage result = new ForumMessage();
		result.setCreation(new Date(System.currentTimeMillis() - 1));
		result.setForum(forum);
		result.setUser(auth);

		return result;
	}

	@Override
	public void validate(final Request<ForumMessage> request, final ForumMessage entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("confirmation")) {
			errors.state(request, request.getModel().getBoolean("confirmation"), "confirmation", "authenticated.forum-message.error.confirmation");
		}
	}

	@Override
	public void create(final Request<ForumMessage> request, final ForumMessage entity) {
		assert request != null;
		assert entity != null;

		Forum forum = this.repository.findForumById(request.getModel().getInteger("forumId"));
		Authenticated auth = this.repository.findAuthenticatedByUserAccountId(request.getPrincipal().getAccountId());

		entity.setCreation(new Date(System.currentTimeMillis() - 1));
		entity.setForum(forum);
		entity.setUser(auth);

		this.repository.save(entity);
	}

}
