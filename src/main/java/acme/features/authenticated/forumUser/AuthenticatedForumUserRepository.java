
package acme.features.authenticated.forumUser;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import acme.entities.forums.Forum;
import acme.entities.forums.ForumUser;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

public interface AuthenticatedForumUserRepository extends AbstractRepository {

	@Query("select f from Forum f where f.id = ?1")
	Forum findForumById(int id);

	@Query("select fu from ForumUser fu where fu.user.userAccount.id = ?1 and fu.forum.id = ?2")
	ForumUser findForumUserByUserAccountIdAndForumId(int userAccountId, int forumId);

	@Query("select a from Authenticated a where a.userAccount.username = ?1")
	Authenticated findAuthenticatedByUsername(String username);

	@Query("select fu.user from ForumUser fu where fu.forum.id = ?1")
	Set<Authenticated> findManyAllUsersByForumId(int id);

	@Query("select a from Authenticated a")
	Set<Authenticated> findManyAllAuthenticated();
}
