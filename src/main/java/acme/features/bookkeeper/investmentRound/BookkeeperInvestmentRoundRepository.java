
package acme.features.bookkeeper.investmentRound;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.repositories.AbstractRepository;

public interface BookkeeperInvestmentRoundRepository extends AbstractRepository {

	@Query("select i from InvestmentRound i where i.id=?1")
	InvestmentRound findOneById(int id);

	@Query("select a.investmentRound from AccountingRecord a where a.bookkeeper.userAccount.id=?1")
	Collection<InvestmentRound> findManyAllWritten(int id);

	@Query("select i from InvestmentRound i")
	Collection<InvestmentRound> findManyAll();
}
