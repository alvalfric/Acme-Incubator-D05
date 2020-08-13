
package acme.features.bookkeeper.accountingRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Bookkeeper;
import acme.framework.repositories.AbstractRepository;

public interface BookkeeperAccountingRecordRepository extends AbstractRepository {

	@Query("select a from AccountingRecord a where a.id=?1")
	AccountingRecord findOneById(int id);

	@Query("select i from InvestmentRound i where i.id=?1")
	InvestmentRound findOneInvestmentRoundById(int id);

	@Query("select a from AccountingRecord a where a.investmentRound.id=?1")
	Collection<AccountingRecord> findManyAllPublishedByInvestmentRoundId(int id);

	@Query("select b from Bookkeeper b where b.userAccount.id=?1")
	Bookkeeper findBookkeeperByUserAccountId(int id);
}
