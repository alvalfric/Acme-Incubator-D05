
package acme.features.investor.application;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;

import acme.entities.investmentRounds.Application;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Investor;
import acme.framework.repositories.AbstractRepository;

public interface InvestorApplicationRoundRepository extends AbstractRepository {

	@Query("select a from Application a where a.id=?1")
	Application findOneById(int id);

	@Query("select a from Application a where a.investmentRound.id = ?1 and investor.id = ?2")
	Application findOneApplicationByIdInvestmentRoundIdAndInvestorId(int investmentRoundId, int investorId);

	@Query("select a from Application a where a.ticker=?1")
	Application findOneApplicationByTicker(String ticker);

	@Query("select i from InvestmentRound i where i.id=?1")
	InvestmentRound findOneInvestmentRoundById(int id);

	@Query("select i from InvestmentRound i where i.ticker=?1")
	InvestmentRound findOneInvestmentRoundByTicker(String ticker);

	@Query("select a from Application a where a.investor.userAccount.id=?1")
	Collection<Application> findManyAllByEntrepeneur(int id);

	@Query("select i from Investor i where i.userAccount.id = ?1")
	Investor findInvestorByUserAccountId(int id);

	@Query("select max(a.deadline) from Activity a where a.investmentRound.id = ?1")
	Date findMaxDeadlineByInvestmentId(int id);
}
