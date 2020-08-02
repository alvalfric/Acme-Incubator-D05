
package acme.features.administrator.notice;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.notices.Notice;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractCreateService;

@Service
public class AdministratorNoticeCreateService implements AbstractCreateService<Administrator, Notice> {

	@Autowired
	private AdministratorNoticeRepository repository;


	@Override
	public boolean authorise(final Request<Notice> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Notice> request, final Notice entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creation");
	}

	@Override
	public void unbind(final Request<Notice> request, final Notice entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "headerPicture", "title", "creation", "deadline", "body", "relatedLink1", "relatedLink2");

		if (request.isMethod(HttpMethod.GET)) {
			model.setAttribute("checkbox", "false");
		} else {
			request.transfer(model, "checkbox");
		}
	}

	@Override
	public Notice instantiate(final Request<Notice> request) {
		Notice result;

		result = new Notice();
		result.setCreation(new Date(System.currentTimeMillis() - 1));

		return result;
	}

	@Override
	public void validate(final Request<Notice> request, final Notice entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("deadline")) {
			if (entity.getDeadline() == null) {
				errors.state(request, entity.getDeadline() != null, "deadline", "administrator.notice.error.null");
			} else {
				errors.state(request, !entity.getDeadline().before(new Date(System.currentTimeMillis())), "deadline", "administrator.notice.error.futuro-deadline");
			}
		}

		if (!errors.hasErrors("checkbox")) {
			errors.state(request, request.getModel().getBoolean("checkbox"), "checkbox", "administrator.notice.error.must-confirm");
		}
	}

	@Override
	public void create(final Request<Notice> request, final Notice entity) {
		Date creation;
		creation = new Date(System.currentTimeMillis() - 1);

		entity.setCreation(creation);
		this.repository.save(entity);
	}

}
