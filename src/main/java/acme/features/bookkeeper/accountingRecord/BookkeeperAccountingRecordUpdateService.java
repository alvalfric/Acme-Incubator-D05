
package acme.features.bookkeeper.accountingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.roles.Bookkeeper;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractUpdateService;

@Service
public class BookkeeperAccountingRecordUpdateService implements AbstractUpdateService<Bookkeeper, AccountingRecord> {

	@Autowired
	private BookkeeperAccountingRecordRepository repository;


	@Override
	public boolean authorise(final Request<AccountingRecord> request) {
		assert request != null;

		AccountingRecord accountingRecord = this.repository.findOneById(request.getModel().getInteger("id"));
		Bookkeeper bookkeeper = this.repository.findBookkeeperByUserAccountId(request.getPrincipal().getAccountId());

		return accountingRecord.getBookkeeper().equals(bookkeeper);
	}

	@Override
	public void bind(final Request<AccountingRecord> request, final AccountingRecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		AccountingRecord accountingRecord = this.repository.findOneById(request.getModel().getInteger("id"));
		Bookkeeper bookkeeper = this.repository.findBookkeeperByUserAccountId(request.getPrincipal().getAccountId());

		request.getModel().setAttribute("canUpdate", accountingRecord.getBookkeeper().equals(bookkeeper) && accountingRecord.getStatus().equals("draft"));

		request.bind(entity, errors, "creation");
	}

	@Override
	public void unbind(final Request<AccountingRecord> request, final AccountingRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		AccountingRecord accountingRecord = this.repository.findOneById(request.getModel().getInteger("id"));
		Bookkeeper bookkeeper = this.repository.findBookkeeperByUserAccountId(request.getPrincipal().getAccountId());

		model.setAttribute("canUpdate", accountingRecord.getBookkeeper().equals(bookkeeper) && accountingRecord.getStatus().equals("draft"));

		request.unbind(entity, model, "title", "status", "body");
	}

	@Override
	public AccountingRecord findOne(final Request<AccountingRecord> request) {
		assert request != null;

		AccountingRecord result = this.repository.findOneById(request.getModel().getInteger("id"));

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
	public void update(final Request<AccountingRecord> request, final AccountingRecord entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

}
