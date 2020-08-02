
package acme.features.administrator.customizationParameter;

import org.springframework.data.jpa.repository.Query;

import acme.entities.customizationParameters.CustomizationParameter;
import acme.framework.repositories.AbstractRepository;

public interface AdministratorCustomizationParameterRepository extends AbstractRepository {

	@Query("select c from CustomizationParameter c where c.id = ?1")
	CustomizationParameter findOneById(int id);

	@Query("select c from CustomizationParameter c")
	CustomizationParameter find();

}
