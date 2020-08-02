
package acme.features.bookkeeper.accountingRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.accountingRecords.AccountingRecord;
import acme.framework.repositories.AbstractRepository;

public interface BookkeeperAccountingRecordRepository extends AbstractRepository {

	@Query("select a from AccountingRecord a where a.id=?1 and a.status='published'")
	AccountingRecord findOneById(int id);

	@Query("select a from AccountingRecord a where a.investmentRound.id=?1 and a.status='published'")
	Collection<AccountingRecord> findManyAllPublishedByInvestmentRoundId(int id);
}
