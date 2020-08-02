
package acme.features.entrepeneur.investmentRound;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.repositories.AbstractRepository;

public interface EntrepeneurInvestmentRoundRepository extends AbstractRepository {

	@Query("select i from InvestmentRound i where i.id=?1")
	InvestmentRound findOneById(int id);

	@Query("select i from InvestmentRound i where i.entrepeneur.userAccount.id=?1")
	Collection<InvestmentRound> findManyAllByEntrepeneur(int id);
}
