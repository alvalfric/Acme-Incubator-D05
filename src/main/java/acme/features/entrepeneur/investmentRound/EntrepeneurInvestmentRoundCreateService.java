
package acme.features.entrepeneur.investmentRound;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

		String[] activitySectors = this.repository.findCustomizationParameters().getActivitySectors().split(", ");

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

		entity.setCreation(new Date(System.currentTimeMillis() - 1));
		entity.setAmount(money);
		entity.setFinalMode(false);
		entity.setEntrepeneur(this.repository.findOneEntrepeneurByAccountId(request.getPrincipal().getAccountId()));

		this.repository.save(entity);
	}

}
