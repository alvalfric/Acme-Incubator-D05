
package acme.features.authenticated.investmentRound;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;

import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.repositories.AbstractRepository;

public interface AuthenticatedInvestmentRoundRepository extends AbstractRepository {

	@Query("select i from InvestmentRound i, Activity a where i=a.investmentRound and i.id=?1 group by i.id having max(a.deadline) >= CURRENT_TIMESTAMP")
	InvestmentRound findOneByIdActive(int id);

	@Query("select i from InvestmentRound i, Activity a where i=a.investmentRound group by i.id having max(a.deadline) >= CURRENT_TIMESTAMP")
	Collection<InvestmentRound> findManyAllActive();

	@Query("select max(a.deadline) from Activity a where a.investmentRound.id = ?1")
	Date findMaxDeadlineByInvestmentId(int id);
}
