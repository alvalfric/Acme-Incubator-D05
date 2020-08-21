
package acme.features.patron.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.Banner;
import acme.entities.roles.Patron;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class PatronBannerShowService implements AbstractShowService<Patron, Banner> {

	@Autowired
	private PatronBannerRepository repository;


	@Override
	public boolean authorise(final Request<Banner> request) {
		assert request != null;

		Banner banner = this.repository.findOneById(request.getModel().getInteger("id"));

		return banner.getPatron().getUserAccount().getId() == request.getPrincipal().getAccountId();
	}

	@Override
	public void unbind(final Request<Banner> request, final Banner entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "picture", "slogan", "url");
	}

	@Override
	public Banner findOne(final Request<Banner> request) {
		assert request != null;

		Banner result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}

}
