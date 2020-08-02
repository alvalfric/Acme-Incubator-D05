
package acme.features.authenticated.forum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.forums.Forum;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

public interface AuthenticatedForumRepository extends AbstractRepository {

	@Query("select f from Forum f")
	Collection<Forum> findManyAllByAuthenticatedUser(Authenticated u);

	@Query("select f from Forum f")
	Collection<Forum> findManyAll();

	@Query("select f.users from Forum f")
	Collection<Authenticated> findManyAllUsers();

	@Query("select f from Forum f where f.id = ?1")
	Forum findForumById(int id);

	@Query("select a from Authenticated a where a.userAccount.id = ?1")
	Authenticated findAuthenticatedByUserAccountId(int id);
}
