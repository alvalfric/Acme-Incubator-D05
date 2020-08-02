
package acme.features.anonymous.toolRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.toolRecords.ToolRecord;
import acme.framework.repositories.AbstractRepository;

public interface AnonymousToolRecordRepository extends AbstractRepository {

	@Query("select t from ToolRecord t where t.id = ?1")
	ToolRecord findOneById(int id);

	@Query("select t from ToolRecord t order by t.stars desc")
	Collection<ToolRecord> findManyAll();

}
