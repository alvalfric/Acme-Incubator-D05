
package acme.features.entrepeneur.activity;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.investmentRounds.Activity;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepeneur;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class EntrepeneurActivityCreateService implements AbstractCreateService<Entrepeneur, Activity> {

	@Autowired
	private EntrepeneurActivityRepository repository;


	@Override
	public boolean authorise(final Request<Activity> request) {
		assert request != null;

		InvestmentRound investment = this.repository.findOneInvestmentRoundById(request.getModel().getInteger("investmentRoundId"));
		Principal principal = request.getPrincipal();

		return principal.getAccountId() == investment.getEntrepeneur().getUserAccount().getId();
	}

	@Override
	public void bind(final Request<Activity> request, final Activity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creation");
	}

	@Override
	public void unbind(final Request<Activity> request, final Activity entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("investmentRoundId", request.getModel().getInteger("investmentRoundId"));

		request.unbind(entity, model, "title", "creation", "deadline", "money", "investmentRound");
	}

	@Override
	public Activity instantiate(final Request<Activity> request) {
		Activity result;
		InvestmentRound investment = this.repository.findOneInvestmentRoundById(request.getModel().getInteger("investmentRoundId"));

		result = new Activity();
		result.setCreation(new Date(System.currentTimeMillis() - 1));
		result.setInvestmentRound(investment);

		return result;
	}

	@Override
	public void validate(final Request<Activity> request, final Activity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("money")) {
			boolean euroCurrency = entity.getMoney().getCurrency().equals("€") || entity.getMoney().getCurrency().equals("EUR");
			errors.state(request, euroCurrency, "money", "entrepeneur.investment-round.error.amount");
		}
	}

	@Override
	public void create(final Request<Activity> request, final Activity entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

		InvestmentRound investment = entity.getInvestmentRound();
		List<Money> workProgrammeAmounts = this.repository.findManyAllByInvestmentRoundId(entity.getInvestmentRound().getId()).stream().map(Activity::getMoney).collect(Collectors.toList());
		Double totalAmountWorkProgramme = workProgrammeAmounts.stream().map(Money::getAmount).reduce(0.0, Double::sum);

		Money updateAmount = new Money();
		updateAmount.setCurrency(entity.getMoney().getCurrency());
		updateAmount.setAmount(totalAmountWorkProgramme);

		investment.setAmount(updateAmount);

		this.repository.save(investment);
	}

}
