
package acme.features.administrator.overture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.customizationParameters.CustomizationParameter;
import acme.entities.overtures.Overture;
import acme.framework.repositories.AbstractRepository;

public interface AdministratorOvertureRepository extends AbstractRepository {

	@Query("select o from Overture o where o.id = ?1")
	Overture findOneById(int id);

	@Query("select o from Overture o where o.id = ?1 and o.deadline >= CURRENT_TIMESTAMP")
	Overture findOneByIdActive(int id);

	@Query("select o from Overture o")
	Collection<Overture> findManyAll();

	@Query("select o from Overture o where o.deadline >= CURRENT_TIMESTAMP")
	Collection<Overture> findManyAllActive();

	@Query("select c from CustomizationParameter c")
	CustomizationParameter findCustomizationParameters();
}
