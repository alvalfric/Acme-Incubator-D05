
package acme.features.authenticated.forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.forums.ForumUser;
import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedForumCreateService implements AbstractCreateService<Authenticated, Forum> {

	@Autowired
	private AuthenticatedForumRepository repository;


	@Override
	public boolean authorise(final Request<Forum> request) {
		assert request != null;

		InvestmentRound investment = this.repository.findInvestmentRoundById(request.getModel().getInteger("investmentRoundId"));
		Forum forum = this.repository.findForumByInvestmentRoundId(request.getModel().getInteger("investmentRoundId"));
		Principal principal = request.getPrincipal();

		return principal.getAccountId() == investment.getEntrepeneur().getUserAccount().getId() && forum == null && investment.isFinalMode();
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

		request.getModel().setAttribute("investmentRoundId", request.getModel().getInteger("investmentRoundId"));

		request.unbind(entity, model);
	}

	@Override
	public Forum instantiate(final Request<Forum> request) {
		Forum result = new Forum();
		InvestmentRound investment = this.repository.findInvestmentRoundById(request.getModel().getInteger("investmentRoundId"));

		result.setForumTitle(investment.getTicker());
		result.setInvestmentRound(investment);

		return result;
	}

	@Override
	public void validate(final Request<Forum> request, final Forum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void create(final Request<Forum> request, final Forum entity) {
		assert request != null;
		assert entity != null;

		InvestmentRound investment = this.repository.findInvestmentRoundById(request.getModel().getInteger("investmentRoundId"));

		entity.setForumTitle(investment.getTicker());
		entity.setInvestmentRound(investment);
		this.repository.save(entity);

		ForumUser forumUser = new ForumUser();
		forumUser.setUser(this.repository.findAuthenticatedByUserAccountId(request.getPrincipal().getAccountId()));
		forumUser.setForum(this.repository.findForumByInvestmentRoundId(investment.getId()));
		this.repository.save(forumUser);
	}

}
