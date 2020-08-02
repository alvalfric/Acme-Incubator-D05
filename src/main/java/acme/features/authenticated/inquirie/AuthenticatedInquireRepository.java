
package acme.features.authenticated.inquirie;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.inquiries.Inquirie;
import acme.framework.repositories.AbstractRepository;

public interface AuthenticatedInquireRepository extends AbstractRepository {

	@Query("select i from Inquirie i where i.id = ?1")
	Inquirie findOneById(int id);

	@Query("select i from Inquirie i where i.id = ?1 and i.deadline >= CURRENT_TIMESTAMP")
	Inquirie findOneByIdActive(int id);

	@Query("select i from Inquirie i")
	Collection<Inquirie> findManyAll();

	@Query("select i from Inquirie i where i.deadline >= CURRENT_TIMESTAMP")
	Collection<Inquirie> findManyAllActive();
}
