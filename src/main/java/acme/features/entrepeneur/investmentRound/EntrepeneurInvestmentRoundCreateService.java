
package acme.features.entrepeneur.investmentRound;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepeneur;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractCreateService;

@Service
public class EntrepeneurInvestmentRoundCreateService implements AbstractCreateService<Entrepeneur, InvestmentRound> {

	@Autowired
	private EntrepeneurInvestmentRoundRepository repository;


	@Override
	public boolean authorise(final Request<InvestmentRound> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<InvestmentRound> request, final InvestmentRound entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creation");
	}

	@Override
	public void unbind(final Request<InvestmentRound> request, final InvestmentRound entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "creation", "round", "title", "description", "amount", "link", "finalMode");
	}

	@Override
	public InvestmentRound instantiate(final Request<InvestmentRound> request) {
		InvestmentRound result;

		Money money = new Money();
		money.setAmount(0.0);
		money.setCurrency("€");
		result = new InvestmentRound();

		result.setTicker(this.generateTicker(request));
		result.setAmount(money);
		result.setCreation(new Date(System.currentTimeMillis() - 1));
		result.setEntrepeneur(this.repository.findOneEntrepeneurByAccountId(request.getPrincipal().getAccountId()));
		result.setFinalMode(false);

		return result;
	}

	@Override
	public void validate(final Request<InvestmentRound> request, final InvestmentRound entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		entity.setTicker(this.generateTicker(request));

		if (!errors.hasErrors("ticker")) {
			boolean uniqueTicker = this.repository.findOneInvestmentRoundByTicker(entity.getTicker()) == null && this.repository.findOneApplicationByTicker(entity.getTicker()) == null;
			errors.state(request, uniqueTicker, "ticker", "entrepeneur.investment-round.error.unique");
		}

		if (!errors.hasErrors("ticker")) {
			boolean uniqueTicker = this.repository.findOneInvestmentRoundByTicker(entity.getTicker()) == null && this.repository.findOneApplicationByTicker(entity.getTicker()) == null;
			errors.state(request, uniqueTicker, "ticker", "entrepeneur.investment-round.error.unique");
		}

		if (!errors.hasErrors("round")) {
			List<String> rounds = Arrays.asList("SEED", "ANGEL", "SERIES-A", "SERIES-B", "SERIES-C", "BRIDGE");
			boolean acceptedRound = rounds.contains(entity.getRound());
			errors.state(request, acceptedRound, "round", "entrepeneur.investment-round.error.round");
		}

		if (!errors.hasErrors("amount")) {
			boolean euroCurrency = entity.getAmount().getCurrency().equals("€") || entity.getAmount().getCurrency().equals("EUR");
			errors.state(request, euroCurrency, "amount", "entrepeneur.investment-round.error.amount");
		}
	}

	@Override
	public void create(final Request<InvestmentRound> request, final InvestmentRound entity) {
		assert request != null;
		assert entity != null;

		Money money = new Money();
		money.setAmount(0.0);
		money.setCurrency("€");

		entity.setTicker(this.generateTicker(request));
		entity.setCreation(new Date(System.currentTimeMillis() - 1));
		entity.setAmount(money);
		entity.setFinalMode(false);
		entity.setEntrepeneur(this.repository.findOneEntrepeneurByAccountId(request.getPrincipal().getAccountId()));

		this.repository.save(entity);
	}

	private String generateTicker(final Request<InvestmentRound> request) {
		assert request != null;

		String ticker;

		Entrepeneur entrepeneur = this.repository.findOneEntrepeneurByAccountId(request.getPrincipal().getAccountId());
		String activitySectorInitials = entrepeneur.getActivitySector().replaceAll("\\s+", "").substring(0, 3).toUpperCase();

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
