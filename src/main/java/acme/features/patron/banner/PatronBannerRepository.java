
package acme.features.patron.banner;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.banners.Banner;
import acme.framework.repositories.AbstractRepository;

public interface PatronBannerRepository extends AbstractRepository {

	@Query("select b from Banner b where b.id = ?1")
	Banner findOneById(int id);

	@Query("select b from Banner b")
	Collection<Banner> findManyAll();

	@Query("select b from Banner b where b.patron.userAccount.id = ?1")
	Collection<Banner> findManyAllByPatron(int id);
}
