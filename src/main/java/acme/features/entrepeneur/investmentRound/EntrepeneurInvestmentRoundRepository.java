
package acme.features.entrepeneur.investmentRound;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.customizationParameters.CustomizationParameter;
import acme.entities.forums.Forum;
import acme.entities.forums.ForumUser;
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

	@Query("select fu from ForumUser fu where fu.forum.id = ?1")
	Set<ForumUser> findManyAllForumUsersByForumId(int id);

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

	@Query("select cp from CustomizationParameter cp")
	CustomizationParameter findCustomizationParameters();

	@Query("select substring(i.ticker,8,6) from InvestmentRound i where i.ticker like %?1%")
	Set<String> findTickerIdsByTagAndYearFromInvestmentRound(String tagAndYear);

	@Query("select substring(a.ticker,8,6) from Application a where a.ticker like %?1%")
	Set<String> findTickerIdsByTagAndYearFromApplication(String tagAndYear);
}
