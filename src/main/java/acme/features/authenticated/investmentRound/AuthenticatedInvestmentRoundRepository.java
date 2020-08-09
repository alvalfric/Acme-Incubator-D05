
package acme.features.authenticated.investmentRound;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;

import acme.entities.forums.Forum;
import acme.entities.investmentRounds.Activity;
import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.repositories.AbstractRepository;

public interface AuthenticatedInvestmentRoundRepository extends AbstractRepository {

	@Query("select i from InvestmentRound i, Activity a where i=a.investmentRound and i.id=?1 group by i.id having max(a.deadline) >= CURRENT_TIMESTAMP and i.finalMode = 1")
	InvestmentRound findOneByIdActive(int id);

	@Query("select f from Forum f where f.investmentRound.id = ?1")
	Forum findOneForumByInvestmentRoundId(int id);

	@Query("select i from InvestmentRound i, Activity a where i=a.investmentRound group by i.id having max(a.deadline) >= CURRENT_TIMESTAMP and i.finalMode = 1")
	Collection<InvestmentRound> findManyAllActive();

	@Query("select a from Activity a where a.investmentRound.id=?1")
	Collection<Activity> findManyAllActivityByInvestmentRoundId(int id);

	@Query("select max(a.deadline) from Activity a where a.investmentRound.id = ?1")
	Date findMaxDeadlineByInvestmentId(int id);
}
