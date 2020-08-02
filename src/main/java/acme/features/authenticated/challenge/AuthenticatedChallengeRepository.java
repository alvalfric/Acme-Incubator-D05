
package acme.features.authenticated.challenge;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.challenges.Challenge;
import acme.framework.repositories.AbstractRepository;

public interface AuthenticatedChallengeRepository extends AbstractRepository {

	@Query("select c from Challenge c where c.id = ?1")
	Challenge findOneById(int id);

	@Query("select c from Challenge c where c.id = ?1 and c.deadline >= CURRENT_TIMESTAMP")
	Challenge findOneByIdActive(int id);

	@Query("select c from Challenge c")
	Collection<Challenge> findManyAll();

	@Query("select c from Challenge c where c.deadline >= CURRENT_TIMESTAMP")
	Collection<Challenge> findManyAllActive();
}
