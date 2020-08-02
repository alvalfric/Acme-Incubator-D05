
package acme.features.entrepeneur.accountingRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepeneur;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;

@Service
public class EntrepeneurAccountingRecordListService implements AbstractListService<Entrepeneur, AccountingRecord> {

	@Autowired
	private EntrepeneurAccountingRecordRepository repository;


	@Override
	public boolean authorise(final Request<AccountingRecord> request) {
		assert request != null;

		InvestmentRound investmentRound = this.repository.findOneInvestmentRoundById(request.getModel().getInteger("investmentRoundId"));
		Principal principal = request.getPrincipal();

		return investmentRound.getEntrepeneur().getUserAccount().getId() == principal.getAccountId();
	}

	@Override
	public void unbind(final Request<AccountingRecord> request, final AccountingRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "creation");
	}

	@Override
	public Collection<AccountingRecord> findMany(final Request<AccountingRecord> request) {
		assert request != null;

		Collection<AccountingRecord> result;

		result = this.repository.findManyAllById(request.getModel().getInteger("investmentRoundId"));

		return result;
	}

}
