
package acme.features.entrepeneur.activity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.customizationParameters.CustomizationParameter;
import acme.entities.investmentRounds.Activity;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepeneur;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class EntrepeneurActivityUpdateService implements AbstractUpdateService<Entrepeneur, Activity> {

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

		boolean titleIsSpam = this.spamChecker(entity.getTitle());

		if (!errors.hasErrors("title")) {
			errors.state(request, !titleIsSpam, "title", "entrepeneur.investment-round.error.spam");
		}

		if (!errors.hasErrors("money")) {
			boolean euroCurrency = entity.getMoney().getCurrency().equals("€") || entity.getMoney().getCurrency().equals("EUR");
			errors.state(request, euroCurrency, "money", "entrepeneur.investment-round.error.amount");
		}
	}

	@Override
	public void update(final Request<Activity> request, final Activity entity) {
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
