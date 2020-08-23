
package acme.features.entrepeneur.application;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.forums.Forum;
import acme.entities.investmentRounds.Application;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

public interface EntrepeneurApplicationRepository extends AbstractRepository {

	@Query("select a from Application a where a.id=?1")
	Application findOneById(int id);

	@Query("select f from Forum f where f.investmentRound.id=?1")
	Forum findOneForumByInvestmentRoundId(int id);

	@Query("select a from Application a where a.investmentRound.entrepeneur.userAccount.id=?1")
	Collection<Application> findManyAllByEntrepeneur(int id);

	@Query("select a from Application a where a.investmentRound.entrepeneur.userAccount.id=?1 order by a.ticker")
	Collection<Application> findManyAllByEntrepeneurGroupByTicker(int id);

	@Query("select a from Application a where a.investmentRound.entrepeneur.userAccount.id=?1 order by a.creation desc")
	Collection<Application> findManyAllByEntrepeneurGroupByCreation(int id);

	@Query("select a from Authenticated a where a.userAccount.id=?1")
	Authenticated findOneAuthenticatedByUserAccountId(int id);
}
