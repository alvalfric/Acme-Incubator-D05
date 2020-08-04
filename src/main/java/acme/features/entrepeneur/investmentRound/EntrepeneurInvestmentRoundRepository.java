
package acme.features.entrepeneur.investmentRound;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.forums.Forum;
import acme.entities.investmentRounds.Activity;
import acme.entities.investmentRounds.Application;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepeneur;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

public interface EntrepeneurInvestmentRoundRepository extends AbstractRepository {

	@Query("select i from InvestmentRound i where i.id=?1")
	InvestmentRound findOneById(int id);

	@Query("select i from InvestmentRound i where i.ticker=?1")
	InvestmentRound findOneInvestmentRoundByTicker(String ticker);

	@Query("select a from Application a where a.ticker=?1")
	Application findOneApplicationByTicker(String ticker);

	@Query("select f from Forum f where f.investmentRound.id=?1")
	Forum findOneForumByInvestmentRoundId(int id);

	@Query("select i from InvestmentRound i where i.entrepeneur.userAccount.id=?1")
	Collection<InvestmentRound> findManyAllByEntrepeneur(int id);

	@Query("select ar from AccountingRecord ar where ar.investmentRound=?1")
	Collection<AccountingRecord> findManyAllAccountingRecordsByInvestmentRound(InvestmentRound i);

	@Query("select a from Application a where a.investmentRound.id=?1")
	Collection<Application> findManyAllApplicationsByInvestmentRoundId(int id);

	@Query("select a from Activity a where a.investmentRound.id=?1")
	Collection<Activity> findManyAllActivityByInvestmentRoundId(int id);

	@Query("select e from Entrepeneur e where e.userAccount.id=?1")
	Entrepeneur findOneEntrepeneurByAccountId(int id);

	@Query("select a from Authenticated a where a.userAccount.id=?1")
	Authenticated findOneAuthenticatedByAccountId(int id);
}
