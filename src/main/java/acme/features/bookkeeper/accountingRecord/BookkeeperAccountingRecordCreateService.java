
package acme.features.bookkeeper.accountingRecord;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Bookkeeper;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class BookkeeperAccountingRecordCreateService implements AbstractCreateService<Bookkeeper, AccountingRecord> {

	@Autowired
	private BookkeeperAccountingRecordRepository repository;


	@Override
	public boolean authorise(final Request<AccountingRecord> request) {
		assert request != null;

		InvestmentRound investmentRound = this.repository.findOneInvestmentRoundById(request.getModel().getInteger("investmentRoundId"));

		return investmentRound.isFinalMode();
	}

	@Override
	public void bind(final Request<AccountingRecord> request, final AccountingRecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creation");
	}

	@Override
	public void unbind(final Request<AccountingRecord> request, final AccountingRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("investmentRoundId", request.getModel().getInteger("investmentRoundId"));

		request.unbind(entity, model, "title", "status", "body");
	}

	@Override
	public AccountingRecord instantiate(final Request<AccountingRecord> request) {
		assert request != null;

		AccountingRecord result = new AccountingRecord();
		InvestmentRound investmentRound = this.repository.findOneInvestmentRoundById(request.getModel().getInteger("investmentRoundId"));
		Bookkeeper bookkeeper = this.repository.findBookkeeperByUserAccountId(request.getPrincipal().getAccountId());

		result.setCreation(new Date(System.currentTimeMillis() - 1));
		result.setStatus("draft");
		result.setInvestmentRound(investmentRound);
		result.setBookkeeper(bookkeeper);

		return result;
	}

	@Override
	public void validate(final Request<AccountingRecord> request, final AccountingRecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("status")) {
			boolean statusOk = entity.getStatus().equals("draft") || entity.getStatus().equals("published");
			errors.state(request, statusOk, "status", "bookkeeper.accountingRecord.error.status");
		}
	}

	@Override
	public void create(final Request<AccountingRecord> request, final AccountingRecord entity) {
		assert request != null;
		assert entity != null;

		InvestmentRound investmentRound = this.repository.findOneInvestmentRoundById(request.getModel().getInteger("investmentRoundId"));
		Bookkeeper bookkeeper = this.repository.findBookkeeperByUserAccountId(request.getPrincipal().getAccountId());

		entity.setCreation(new Date(System.currentTimeMillis() - 1));
		entity.setInvestmentRound(investmentRound);
		entity.setBookkeeper(bookkeeper);

		this.repository.save(entity);
	}

}
