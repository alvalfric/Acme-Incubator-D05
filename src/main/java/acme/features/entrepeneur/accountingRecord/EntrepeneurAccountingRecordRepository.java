
package acme.features.entrepeneur.accountingRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.repositories.AbstractRepository;

public interface EntrepeneurAccountingRecordRepository extends AbstractRepository {

	@Query("select a from AccountingRecord a where a.id=?1 and a.status='published'")
	AccountingRecord findOneById(int id);

	@Query("select a from AccountingRecord a where a.investmentRound.id=?1 and a.status='published'")
	Collection<AccountingRecord> findManyAllById(int id);

	//	@Query("select a from AccountingRecord a where a.investmentRound.id=?1 and a.investmentRound.entrepeneur.userAccount.id=?2 and a.status='published'")
	//	Collection<AccountingRecord> findManyAllByIdAndEntrepeneur(int id, int userAccountId);

	@Query("select i from InvestmentRound i where i.id=?1")
	InvestmentRound findOneInvestmentRoundById(int id);
}
