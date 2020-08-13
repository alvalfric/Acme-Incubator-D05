
package acme.features.administrator.customizationParameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.customizationParameters.CustomizationParameter;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorCustomizationParameterShowService implements AbstractShowService<Administrator, CustomizationParameter> {

	@Autowired
	private AdministratorCustomizationParameterRepository repository;


	@Override
	public boolean authorise(final Request<CustomizationParameter> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<CustomizationParameter> request, final CustomizationParameter entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "spamWordsEnglish", "spamWordsSpanish", "spamThreshold", "activitySectors");
	}

	@Override
	public CustomizationParameter findOne(final Request<CustomizationParameter> request) {
		assert request != null;

		CustomizationParameter result;

		result = this.repository.find();

		return result;
	}

}
