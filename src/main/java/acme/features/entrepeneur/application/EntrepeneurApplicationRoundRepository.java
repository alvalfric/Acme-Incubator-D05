
package acme.features.entrepeneur.application;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.investmentRounds.Application;
import acme.framework.repositories.AbstractRepository;

public interface EntrepeneurApplicationRoundRepository extends AbstractRepository {

	@Query("select a from Application a where a.id=?1")
	Application findOneById(int id);

	@Query("select a from Application a where a.investmentRound.entrepeneur.userAccount.id=?1")
	Collection<Application> findManyAllByEntrepeneur(int id);
}
