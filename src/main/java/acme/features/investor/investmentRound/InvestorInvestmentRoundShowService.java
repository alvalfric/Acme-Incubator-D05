
package acme.features.investor.investmentRound;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.investmentRounds.Activity;
import acme.entities.investmentRounds.Application;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Investor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class InvestorInvestmentRoundShowService implements AbstractShowService<Investor, InvestmentRound> {

	@Autowired
	private InvestorInvestmentRoundRepository repository;


	@Override
	public boolean authorise(final Request<InvestmentRound> request) {
		assert request != null;

		InvestmentRound investment = this.repository.findOneByIdActive(request.getModel().getInteger("id"));

		return investment.isFinalMode();
	}

	@Override
	public void unbind(final Request<InvestmentRound> request, final InvestmentRound entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		Collection<Activity> workProgramme = this.repository.findManyAllActivityByInvestmentRoundId(entity.getId());
		Investor investor = this.repository.findInvestorByUserAccountId(request.getPrincipal().getAccountId());
		Application app = this.repository.findOneApplicationByIdInvestmentRoundIdAndInvestorId(entity.getId(), investor.getId());
		boolean canApply = app == null;

		model.setAttribute("workProgramme", workProgramme);
		model.setAttribute("canApply", canApply);
		if (app != null) {
			model.setAttribute("applicationId", app.getId());
		}

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
