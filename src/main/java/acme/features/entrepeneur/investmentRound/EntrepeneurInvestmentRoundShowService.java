
package acme.features.entrepeneur.investmentRound;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.investmentRounds.Activity;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepeneur;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class EntrepeneurInvestmentRoundShowService implements AbstractShowService<Entrepeneur, InvestmentRound> {

	@Autowired
	private EntrepeneurInvestmentRoundRepository repository;


	@Override
	public boolean authorise(final Request<InvestmentRound> request) {
		assert request != null;

		InvestmentRound investment = this.repository.findOneById(request.getModel().getInteger("id"));
		Principal principal = request.getPrincipal();

		return principal.getAccountId() == investment.getEntrepeneur().getUserAccount().getId();
	}

	@Override
	public void unbind(final Request<InvestmentRound> request, final InvestmentRound entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		Collection<Activity> workProgramme = this.repository.findManyAllActivityByInvestmentRoundId(entity.getId());
		model.setAttribute("workProgramme", workProgramme);
		model.setAttribute("canBeDeleted", this.repository.findManyAllApplicationsByInvestmentRoundId(request.getModel().getInteger("id")).isEmpty());

		Forum forum = this.repository.findOneForumByInvestmentRoundId(request.getModel().getInteger("id"));
		InvestmentRound investmentSaved = this.repository.findOneById(entity.getId());
		model.setAttribute("canCreateForum", forum == null && entity.getEntrepeneur().getUserAccount().getId() == request.getPrincipal().getAccountId() && investmentSaved.isFinalMode());

		model.setAttribute("isFinalMode", entity.isFinalMode());

		request.unbind(entity, model, "ticker", "creation", "round", "title", "description", "amount", "link", "finalMode");
	}

	@Override
	public InvestmentRound findOne(final Request<InvestmentRound> request) {
		assert request != null;

		InvestmentRound result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}
}
