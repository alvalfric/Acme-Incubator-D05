
package acme.features.administrator.technologyRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.technologyRecords.TechnologyRecord;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractCreateService;

@Service
public class AdministratorTechnologyRecordCreateService implements AbstractCreateService<Administrator, TechnologyRecord> {

	@Autowired
	private AdministratorTechnologyRecordRepository repository;


	@Override
	public boolean authorise(final Request<TechnologyRecord> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<TechnologyRecord> request, final TechnologyRecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		String[] activitySectors = this.repository.findCustomizationParameters().getActivitySectors().split(", ");
		request.getModel().setAttribute("activitySectors", activitySectors);

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<TechnologyRecord> request, final TechnologyRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		String[] activitySectors = this.repository.findCustomizationParameters().getActivitySectors().split(", ");
		model.setAttribute("activitySectors", activitySectors);

		request.unbind(entity, model, "title", "activitySector", "inventorName", "description", "website", "email", "sourceType", "stars");
	}

	@Override
	public TechnologyRecord instantiate(final Request<TechnologyRecord> request) {
		TechnologyRecord result;

		result = new TechnologyRecord();

		return result;
	}

	@Override
	public void validate(final Request<TechnologyRecord> request, final TechnologyRecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("sourceType")) {
			if (entity.getSourceType() == null) {
				errors.state(request, entity.getSourceType() != null, "sourceType", "administrator.technology-record.error.null");
			} else {
				errors.state(request, entity.getSourceType().equals("Open-Source") || entity.getSourceType().equals("Closed-Source"), "sourceType", "administrator.technology-record.error.source");
			}
		}
	}

	@Override
	public void create(final Request<TechnologyRecord> request, final TechnologyRecord entity) {
		this.repository.save(entity);
	}

}
