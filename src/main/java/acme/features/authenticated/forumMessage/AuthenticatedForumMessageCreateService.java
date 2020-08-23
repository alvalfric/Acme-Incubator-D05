
package acme.features.authenticated.forumMessage;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.customizationParameters.CustomizationParameter;
import acme.entities.forums.Forum;
import acme.entities.forums.ForumMessage;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedForumMessageCreateService implements AbstractCreateService<Authenticated, ForumMessage> {

	@Autowired
	private AuthenticatedForumMessageRepository repository;


	@Override
	public boolean authorise(final Request<ForumMessage> request) {
		assert request != null;

		Authenticated auth = this.repository.findAuthenticatedByUserAccountId(request.getPrincipal().getAccountId());
		Set<Authenticated> users = this.repository.findManyAllUsersByForumId(request.getModel().getInteger("forumId"));

		return users.contains(auth);
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

		boolean titleIsSpam = this.spamChecker(entity.getTitle());
		boolean bodyIsSpam = this.spamChecker(entity.getBody());

		if (!errors.hasErrors("title")) {
			errors.state(request, !titleIsSpam, "title", "authenticated.forum-message.error.spam");
		}

		if (!errors.hasErrors("body")) {
			errors.state(request, !bodyIsSpam, "body", "authenticated.forum-message.error.spam");
		}

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

	private boolean spamChecker(final String str) {
		String strFormatted = str.toLowerCase().trim().replaceAll("\\s+", " ");

		CustomizationParameter cp = this.repository.findCustomizationParameters();
		Double spamThreshold = cp.getSpamThreshold();
		Set<String> spamWords = new HashSet<>();
		spamWords.addAll(Arrays.asList(cp.getSpamWordsEnglish().toString().split(", ")));
		spamWords.addAll(Arrays.asList(cp.getSpamWordsSpanish().toString().split(", ")));

		int spamWordsCounter = 0;

		boolean isSpam = false;

		for (String word : spamWords) {
			if (strFormatted.contains(word)) {
				int i = 0;
				while ((i = str.indexOf(word, i)) != -1) {
					spamWordsCounter++;
					i++;

				}
			}
		}

		if (spamWordsCounter > 0) {
			double spamPercentage = Double.valueOf(spamWordsCounter) / strFormatted.split(" ").length * 100;
			isSpam = spamPercentage >= spamThreshold;
		}

		return isSpam;
	}
}
