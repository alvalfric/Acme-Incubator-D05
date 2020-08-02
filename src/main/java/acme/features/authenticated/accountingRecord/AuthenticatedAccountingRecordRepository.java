
package acme.features.authenticated.accountingRecord;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;

import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.repositories.AbstractRepository;

public interface AuthenticatedAccountingRecordRepository extends AbstractRepository {

	@Query("select a from AccountingRecord a where a.id=?1 and a.status='published'")
	AccountingRecord findOneById(int id);

	@Query("select a from AccountingRecord a where a.investmentRound.id=?1 and a.status='published'")
	Collection<AccountingRecord> findManyAllById(int id);

	@Query("select i from InvestmentRound i where i.id=?1")
	InvestmentRound findOneInvestmentRoundById(int id);

	@Query("select max(a.deadline) from Activity a where a.investmentRound.id = ?1")
	Date findMaxDeadlineByInvestmentId(int id);

}
