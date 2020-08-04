
package acme.features.entrepeneur.investmentRound;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.investmentRounds.Activity;
import acme.entities.investmentRounds.Application;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepeneur;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class EntrepeneurInvestmentRoundUpdateService implements AbstractUpdateService<Entrepeneur, InvestmentRound> {

	@Autowired
	private EntrepeneurInvestmentRoundRepository repository;


	@Override
	public boolean authorise(final Request<InvestmentRound> request) {
		assert request != null;

		InvestmentRound investment = this.repository.findOneById(request.getModel().getInteger("id"));
		Principal principal = request.getPrincipal();

		return principal.getAccountId() == investment.getEntrepeneur().getUserAccount().getId() && investment.isFinalMode() == false;
	}

	@Override
	public void bind(final Request<InvestmentRound> request, final InvestmentRound entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<InvestmentRound> request, final InvestmentRound entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		Collection<Activity> workProgramme = this.repository.findManyAllActivityByInvestmentRoundId(entity.getId());
		model.setAttribute("workProgramme", workProgramme);
		model.setAttribute("canBeDeleted", this.repository.findManyAllApplicationsByInvestmentRoundId(request.getModel().getInteger("id")).isEmpty());

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

	@Override
	public void validate(final Request<InvestmentRound> request, final InvestmentRound entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Collection<Activity> workProgramme = this.repository.findManyAllActivityByInvestmentRoundId(entity.getId());

		if (!errors.hasErrors("ticker")) {
			InvestmentRound inv = this.repository.findOneInvestmentRoundByTicker(entity.getTicker());
			Application app = this.repository.findOneApplicationByTicker(entity.getTicker());

			boolean sameInvestmentRound = inv != null ? inv.equals(entity) : false;
			boolean uniqueTicker = app == null && (sameInvestmentRound || inv == null);

			errors.state(request, uniqueTicker, "ticker", "entrepeneur.investment-round.error.unique");
		}

		if (!errors.hasErrors("round")) {
			List<String> rounds = Arrays.asList("SEED", "ANGEL", "SERIES-A", "SERIES-B", "SERIES-C", "BRIDGE");

			boolean acceptedRound = rounds.contains(entity.getRound());
			errors.state(request, acceptedRound, "round", "entrepeneur.investment-round.error.round");
		}

		if (!errors.hasErrors("workProgramme") && !errors.hasErrors("finalMode") && entity.isFinalMode()) {
			List<Money> workProgrammeAmounts = workProgramme.stream().map(Activity::getMoney).collect(Collectors.toList());
			Double totalAmountWorkProgramme = workProgrammeAmounts.stream().map(Money::getAmount).reduce(0.0, Double::sum);

			boolean canBePublished = workProgramme != null && entity.isFinalMode() && entity.getAmount().getAmount().equals(totalAmountWorkProgramme);
			request.getModel().setAttribute("finalMode", canBePublished);

			errors.state(request, canBePublished, "finalMode", "entrepeneur.investment-round.error.finalMode");
		}
	}

	@Override
	public void update(final Request<InvestmentRound> request, final InvestmentRound entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

		Collection<Activity> workProgramme = this.repository.findManyAllActivityByInvestmentRoundId(entity.getId());
		List<Money> workProgrammeAmounts = workProgramme.stream().map(Activity::getMoney).collect(Collectors.toList());
		Double totalAmountWorkProgramme = workProgrammeAmounts.stream().map(Money::getAmount).reduce(0.0, Double::sum);
		boolean canBePublished = workProgramme != null && entity.isFinalMode() && entity.getAmount().getAmount().equals(totalAmountWorkProgramme);

		if (canBePublished) {
			Forum forum = new Forum();
			forum.setForumTitle("Forum of Investment Round " + entity.getTicker());
			forum.setUsers(new HashSet<>(Arrays.asList(this.repository.findOneAuthenticatedByAccountId(request.getPrincipal().getAccountId()))));
			forum.setInvestmentRound(entity);
			this.repository.save(forum);
		}
	}

}
