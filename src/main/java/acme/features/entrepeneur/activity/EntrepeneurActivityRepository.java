
package acme.features.entrepeneur.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.customizationParameters.CustomizationParameter;
import acme.entities.investmentRounds.Activity;
import acme.entities.investmentRounds.Application;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepeneur;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

public interface EntrepeneurActivityRepository extends AbstractRepository {

	@Query("select a from Activity a where a.id=?1")
	Activity findOneById(int id);

	@Query("select i from InvestmentRound i where i.id=?1")
	InvestmentRound findOneInvestmentRoundById(int id);

	@Query("select a from Application a where a.ticker=?1")
	Application findOneApplicationByTicker(String ticker);

	@Query("select a from Activity a where a.investmentRound.id=?1")
	Collection<Activity> findManyAllByInvestmentRoundId(int id);

	@Query("select ar from AccountingRecord ar where ar.investmentRound=?1")
	Collection<AccountingRecord> findManyAllAccountingRecordsByInvestmentRound(InvestmentRound i);

	@Query("select e from Entrepeneur e where e.userAccount.id=?1")
	Entrepeneur findOneEntrepeneurByAccountId(int id);

	@Query("select a from Authenticated a where a.userAccount.id=?1")
	Authenticated findOneAuthenticatedByAccountId(int id);

	@Query("select cp from CustomizationParameter cp")
	CustomizationParameter findCustomizationParameters();
}
