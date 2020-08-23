
package acme.features.entrepeneur.investmentRound;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.customizationParameters.CustomizationParameter;
import acme.entities.forums.Forum;
import acme.entities.forums.ForumUser;
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

		Collection<Activity> workProgramme = this.repository.findManyAllActivityByInvestmentRoundId(entity.getId());
		request.getModel().setAttribute("workProgramme", workProgramme);
		request.getModel().setAttribute("canBeDeleted", this.repository.findManyAllApplicationsByInvestmentRoundId(request.getModel().getInteger("id")).isEmpty());

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<InvestmentRound> request, final InvestmentRound entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		Collection<Activity> workProgramme = this.repository.findManyAllActivityByInvestmentRoundId(entity.getId());
		model.setAttribute("workProgramme", workProgramme);
		model.setAttribute("canBeDeleted", this.repository.findManyAllApplicationsByInvestmentRoundId(request.getModel().getInteger("id")).isEmpty() && entity.isFinalMode());

		Forum forum = this.repository.findOneForumByInvestmentRoundId(request.getModel().getInteger("id"));
		InvestmentRound investmentSaved = this.repository.findOneById(entity.getId());
		model.setAttribute("canCreateForum", forum == null && entity.getEntrepeneur().getUserAccount().getId() == request.getPrincipal().getAccountId() && investmentSaved.isFinalMode());

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

		boolean titleIsSpam = this.spamChecker(entity.getTitle());
		boolean descriptionIsSpam = this.spamChecker(entity.getDescription());
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

		if (!errors.hasErrors("title") && entity.isFinalMode()) {
			errors.state(request, !titleIsSpam, "title", "entrepeneur.investment-round.error.spam");
		}

		if (!errors.hasErrors("description") && entity.isFinalMode()) {
			errors.state(request, !descriptionIsSpam, "description", "entrepeneur.investment-round.error.spam");
		}

		if (!errors.hasErrors("workProgramme") && !errors.hasErrors("finalMode") && entity.isFinalMode()) {
			List<Money> workProgrammeAmounts = workProgramme.stream().map(Activity::getMoney).collect(Collectors.toList());
			Double totalAmountWorkProgramme = workProgrammeAmounts.stream().map(Money::getAmount).reduce(0.0, Double::sum);

			boolean workProgrammeOk = false;
			if (workProgramme != null) {
				workProgrammeOk = !workProgramme.isEmpty();
			}

			boolean canBePublished = workProgrammeOk && entity.isFinalMode() && entity.getAmount().getAmount().equals(totalAmountWorkProgramme) && !titleIsSpam && !descriptionIsSpam;
			request.getModel().setAttribute("finalMode", canBePublished);

			errors.state(request, canBePublished, "finalMode", "entrepeneur.investment-round.error.finalMode");
			errors.state(request, workProgrammeOk, "finalMode", "entrepeneur.investment-round.error.finalMode.workProgramme");
			errors.state(request, entity.isFinalMode(), "finalMode", "entrepeneur.investment-round.error.finalMode.isFinalMode");
			if (workProgrammeOk) {
				errors.state(request, entity.getAmount().getAmount().equals(totalAmountWorkProgramme), "finalMode", "entrepeneur.investment-round.error.finalMode.amount");
			}
			errors.state(request, !titleIsSpam, "finalMode", "entrepeneur.investment-round.error.finalMode.titleSpam");
			errors.state(request, !descriptionIsSpam, "finalMode", "entrepeneur.investment-round.error.finalMode.descriptionSpam");

		}
	}

	@Override
	public void update(final Request<InvestmentRound> request, final InvestmentRound entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

		boolean titleIsSpam = this.spamChecker(entity.getTitle());
		boolean descriptionIsSpam = this.spamChecker(entity.getDescription());
		Collection<Activity> workProgramme = this.repository.findManyAllActivityByInvestmentRoundId(entity.getId());
		List<Money> workProgrammeAmounts = workProgramme.stream().map(Activity::getMoney).collect(Collectors.toList());
		Double totalAmountWorkProgramme = workProgrammeAmounts.stream().map(Money::getAmount).reduce(0.0, Double::sum);

		boolean workProgrammeOk = false;
		if (workProgramme != null) {
			workProgrammeOk = !workProgramme.isEmpty();
		}

		boolean canBePublished = workProgrammeOk && entity.isFinalMode() && entity.getAmount().getAmount().equals(totalAmountWorkProgramme) && !titleIsSpam && !descriptionIsSpam;

		if (canBePublished) {
			Forum forum = new Forum();
			forum.setForumTitle(entity.getTicker());
			forum.setInvestmentRound(entity);
			this.repository.save(forum);

			ForumUser forumUser = new ForumUser();
			forumUser.setUser(this.repository.findOneAuthenticatedByAccountId(request.getPrincipal().getAccountId()));
			forumUser.setForum(forum);
			this.repository.save(forumUser);
		}
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
