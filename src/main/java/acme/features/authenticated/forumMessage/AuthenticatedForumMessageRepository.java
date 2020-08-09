
package acme.features.authenticated.forumMessage;

import org.springframework.data.jpa.repository.Query;

import acme.entities.forums.Forum;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

public interface AuthenticatedForumMessageRepository extends AbstractRepository {

	@Query("select f from Forum f where f.id = ?1")
	Forum findForumById(int id);

	@Query("select a from Authenticated a where a.userAccount.id = ?1")
	Authenticated findAuthenticatedByUserAccountId(int id);
}
