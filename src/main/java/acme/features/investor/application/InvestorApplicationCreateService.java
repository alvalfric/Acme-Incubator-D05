
package acme.features.investor.application;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.investmentRounds.Application;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Investor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class InvestorApplicationCreateService implements AbstractCreateService<Investor, Application> {

	@Autowired
	private InvestorApplicationRepository repository;


	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;

		Integer investmentRoundId = request.getModel().getInteger("investmentRoundId");
		InvestmentRound investment = this.repository.findOneInvestmentRoundById(investmentRoundId);
		Date deadline = this.repository.findMaxDeadlineByInvestmentId(investmentRoundId);
		Investor investor = this.repository.findInvestorByUserAccountId(request.getPrincipal().getAccountId());
		Application app = this.repository.findOneApplicationByIdInvestmentRoundIdAndInvestorId(investmentRoundId, investor.getId());

		boolean canApply = investment.isFinalMode() && deadline.after(new Date()) && app == null;

		return canApply;
	}

	@Override
	public void bind(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Integer investmentRoundId = request.getModel().getInteger("investmentRoundId");
		InvestmentRound investment = this.repository.findOneInvestmentRoundById(investmentRoundId);
		request.getModel().setAttribute("investmentRound", investment);

		request.bind(entity, errors, "creation");
	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		Integer investmentRoundId = request.getModel().getInteger("investmentRoundId");
		InvestmentRound investment = this.repository.findOneInvestmentRoundById(investmentRoundId);
		model.setAttribute("investmentRound", investment);
		model.setAttribute("investmentRoundId", investmentRoundId);

		request.unbind(entity, model, "ticker", "creation", "statement", "offer", "status", "rejectJustification");
	}

	@Override
	public Application instantiate(final Request<Application> request) {
		assert request != null;

		Application result = new Application();
		result.setTicker(this.generateTicker(request));
		result.setCreation(new Date(System.currentTimeMillis() - 1));
		result.setStatus("pending");
		result.setInvestor(this.repository.findInvestorByUserAccountId(request.getPrincipal().getAccountId()));
		result.setInvestmentRound(this.repository.findOneInvestmentRoundById(request.getModel().getInteger("investmentRoundId")));

		return result;
	}

	@Override
	public void validate(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		entity.setTicker(this.generateTicker(request));

		if (!errors.hasErrors("ticker")) {
			boolean uniqueTicker = this.repository.findOneInvestmentRoundByTicker(entity.getTicker()) == null && this.repository.findOneApplicationByTicker(entity.getTicker()) == null;
			errors.state(request, uniqueTicker, "ticker", "investor.application.error.unique");
		}

		if (!errors.hasErrors("offer")) {
			boolean euroCurrency = entity.getOffer().getCurrency().equals("€") || entity.getOffer().getCurrency().equals("EUR");
			errors.state(request, euroCurrency, "offer", "investor.application.error.offer");
		}
	}

	@Override
	public void create(final Request<Application> request, final Application entity) {
		assert request != null;
		assert entity != null;

		entity.setTicker(this.generateTicker(request));
		entity.setCreation(new Date(System.currentTimeMillis() - 1));
		entity.setInvestor(this.repository.findInvestorByUserAccountId(request.getPrincipal().getAccountId()));
		entity.setStatus("pending");
		entity.setRejectJustification("");
		entity.setInvestor(this.repository.findInvestorByUserAccountId(request.getPrincipal().getAccountId()));
		entity.setInvestmentRound(this.repository.findOneInvestmentRoundById(request.getModel().getInteger("investmentRoundId")));

		this.repository.save(entity);
	}

	private String generateTicker(final Request<Application> request) {
		assert request != null;

		String ticker;

		Investor investor = this.repository.findInvestorByUserAccountId(request.getPrincipal().getAccountId());
		String activitySectorInitials = investor.getActivitySector().replaceAll("\\s+", "").substring(0, 3).toUpperCase();

		if (activitySectorInitials.length() < 3) {
			activitySectorInitials = activitySectorInitials + StringUtils.repeat("X", 3 - activitySectorInitials.length());
		}

		String lastTwoDigitsYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2, 4);

		Set<Integer> tickerIds = this.repository.findTickerIdsByTagAndYearFromInvestmentRound(activitySectorInitials + "-" + lastTwoDigitsYear + "-").stream().map(x -> Integer.valueOf(x)).collect(Collectors.toSet());
		tickerIds.addAll(this.repository.findTickerIdsByTagAndYearFromApplication(activitySectorInitials + "-" + lastTwoDigitsYear + "-").stream().map(x -> Integer.valueOf(x)).collect(Collectors.toSet()));
		int id = -1;

		for (int i = 0; i < tickerIds.size() + 1; i++) {
			if (!tickerIds.contains(i)) {
				id = i;
				break;
			}
		}

		int numberOfZeros = 6 - String.valueOf(id).length();
		String stringId = StringUtils.repeat("0", numberOfZeros) + String.valueOf(id);

		ticker = (activitySectorInitials + "-" + lastTwoDigitsYear + "-" + stringId).toUpperCase();

		return ticker;
	}
}
