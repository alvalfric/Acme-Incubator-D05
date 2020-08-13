
package acme.features.bookkeeper.investmentRound;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.investmentRounds.Activity;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Bookkeeper;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class BookkeeperInvestmentRoundShowService implements AbstractShowService<Bookkeeper, InvestmentRound> {

	@Autowired
	private BookkeeperInvestmentRoundRepository repository;


	@Override
	public boolean authorise(final Request<InvestmentRound> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<InvestmentRound> request, final InvestmentRound entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		Collection<Activity> workProgramme = this.repository.findManyAllActivityByInvestmentRoundId(entity.getId());
		AccountingRecord accountingRecord = this.repository.findAccountingRecordByInvestmentRoundIdAndUserAccountId(entity.getId(), request.getPrincipal().getAccountId());
		model.setAttribute("workProgramme", workProgramme);
		model.setAttribute("canCreateAccountingRecord", accountingRecord == null);

		request.unbind(entity, model, "ticker", "creation", "round", "title", "description", "amount", "link");
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
