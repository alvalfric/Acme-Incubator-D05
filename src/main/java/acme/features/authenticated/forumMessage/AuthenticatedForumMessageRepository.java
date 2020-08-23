
package acme.features.authenticated.forumMessage;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import acme.entities.customizationParameters.CustomizationParameter;
import acme.entities.forums.Forum;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

public interface AuthenticatedForumMessageRepository extends AbstractRepository {

	@Query("select f from Forum f where f.id = ?1")
	Forum findForumById(int id);

	@Query("select a from Authenticated a where a.userAccount.id = ?1")
	Authenticated findAuthenticatedByUserAccountId(int id);

	@Query("select fu.user from ForumUser fu where fu.forum.id = ?1")
	Set<Authenticated> findManyAllUsersByForumId(int id);

	@Query("select cp from CustomizationParameter cp")
	CustomizationParameter findCustomizationParameters();
}
