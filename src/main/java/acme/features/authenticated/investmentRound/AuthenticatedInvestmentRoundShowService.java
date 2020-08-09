
package acme.features.authenticated.investmentRound;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.investmentRounds.Activity;
import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedInvestmentRoundShowService implements AbstractShowService<Authenticated, InvestmentRound> {

	@Autowired
	private AuthenticatedInvestmentRoundRepository repository;


	@Override
	public boolean authorise(final Request<InvestmentRound> request) {
		assert request != null;

		Date deadline = this.repository.findMaxDeadlineByInvestmentId(request.getModel().getInteger("id"));

		boolean active = deadline.after(new Date());

		return active;
	}

	@Override
	public void unbind(final Request<InvestmentRound> request, final InvestmentRound entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		Collection<Activity> workProgramme = this.repository.findManyAllActivityByInvestmentRoundId(entity.getId());
		Forum forum = this.repository.findOneForumByInvestmentRoundId(entity.getId());
		model.setAttribute("workProgramme", workProgramme);
		model.setAttribute("canCreateForum", forum == null && entity.getEntrepeneur().getUserAccount().getId() == request.getPrincipal().getAccountId());

		request.unbind(entity, model, "ticker", "creation", "round", "title", "description", "amount", "link");
	}

	@Override
	public InvestmentRound findOne(final Request<InvestmentRound> request) {
		assert request != null;

		InvestmentRound result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneByIdActive(id);

		return result;
	}
}
