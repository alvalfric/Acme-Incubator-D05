
package acme.features.authenticated.technologyRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.technologyRecords.TechnologyRecord;
import acme.framework.repositories.AbstractRepository;

public interface AuthenticatedTechnologyRecordRepository extends AbstractRepository {

	@Query("select t from TechnologyRecord t where t.id = ?1")
	TechnologyRecord findOneById(int id);

	@Query("select t from TechnologyRecord t order by t.stars desc")
	Collection<TechnologyRecord> findManyAll();

}
