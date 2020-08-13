
package acme.features.administrator.toolRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.customizationParameters.CustomizationParameter;
import acme.entities.toolRecords.ToolRecord;
import acme.framework.repositories.AbstractRepository;

public interface AdministratorToolRecordRepository extends AbstractRepository {

	@Query("select t from ToolRecord t where t.id = ?1")
	ToolRecord findOneById(int id);

	@Query("select t from ToolRecord t order by t.stars desc")
	Collection<ToolRecord> findManyAll();

	@Query("select c from CustomizationParameter c")
	CustomizationParameter findCustomizationParameters();
}
