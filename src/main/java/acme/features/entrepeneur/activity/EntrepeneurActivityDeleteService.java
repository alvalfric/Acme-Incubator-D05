
package acme.features.entrepeneur.activity;

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
import acme.framework.services.AbstractDeleteService;

@Service
public class EntrepeneurActivityDeleteService implements AbstractDeleteService<Entrepeneur, Activity> {

	@Autowired
	private EntrepeneurActivityRepository repository;


	@Override
	public boolean authorise(final Request<Activity> request) {
		assert request != null;

		Activity activity = this.repository.findOneById(request.getModel().getInteger("id"));
		Principal principal = request.getPrincipal();

		return principal.getAccountId() == activity.getInvestmentRound().getEntrepeneur().getUserAccount().getId() && !activity.getInvestmentRound().isFinalMode();
	}

	@Override
	public void bind(final Request<Activity> request, final Activity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Activity> request, final Activity entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "creation", "deadline", "money", "investmentRound");
	}

	@Override
	public Activity findOne(final Request<Activity> request) {
		Activity result;

		result = this.repository.findOneById(request.getModel().getInteger("id"));

		return result;
	}

	@Override
	public void validate(final Request<Activity> request, final Activity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<Activity> request, final Activity entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);

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
