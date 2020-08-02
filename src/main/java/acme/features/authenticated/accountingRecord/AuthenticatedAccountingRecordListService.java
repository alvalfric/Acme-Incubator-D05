
package acme.features.authenticated.accountingRecord;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.accountingRecords.AccountingRecord;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedAccountingRecordListService implements AbstractListService<Authenticated, AccountingRecord> {

	@Autowired
	private AuthenticatedAccountingRecordRepository repository;


	@Override
	public boolean authorise(final Request<AccountingRecord> request) {
		assert request != null;

		Date deadline = this.repository.findMaxDeadlineByInvestmentId(request.getModel().getInteger("investmentRoundId"));

		boolean active = deadline.after(new Date());

		return active;
	}

	@Override
	public void unbind(final Request<AccountingRecord> request, final AccountingRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "status", "creation");
	}

	@Override
	public Collection<AccountingRecord> findMany(final Request<AccountingRecord> request) {
		assert request != null;

		Collection<AccountingRecord> result;
		Principal principal = request.getPrincipal();

		result = this.repository.findManyAllById(request.getModel().getInteger("investmentRoundId"));

		return result;
	}

}
