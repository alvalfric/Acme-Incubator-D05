
package acme.features.authenticated.forum;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import acme.entities.forums.Forum;
import acme.entities.forums.ForumUser;
import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

public interface AuthenticatedForumRepository extends AbstractRepository {

	@Query("select f from Forum f")
	Collection<Forum> findManyAllByAuthenticatedUser(Authenticated u);

	@Query("select f from Forum f")
	Collection<Forum> findManyAll();

	//	@Query("select f.users from Forum f")
	//	Collection<Authenticated> findManyAllUsers();

	@Query("select fu.user from ForumUser fu where fu.forum.id = ?1")
	Set<Authenticated> findManyAllUsersByForumId(int id);

	@Query("select fu from ForumUser fu where fu.forum.id = ?1")
	Set<ForumUser> findManyAllForumUsersByForumId(int id);

	@Query("select i from InvestmentRound i where i.id = ?1")
	InvestmentRound findInvestmentRoundById(int id);

	@Query("select f from Forum f where f.id = ?1")
	Forum findForumById(int id);

	@Query("select f from Forum f where f.investmentRound.id = ?1")
	Forum findForumByInvestmentRoundId(int id);

	@Query("select a from Authenticated a where a.userAccount.id = ?1")
	Authenticated findAuthenticatedByUserAccountId(int id);
}
