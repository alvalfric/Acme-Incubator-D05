
package acme.features.patron.banner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.Banner;
import acme.entities.customizationParameters.CustomizationParameter;
import acme.entities.roles.Patron;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class PatronBannerCreateService implements AbstractCreateService<Patron, Banner> {

	@Autowired
	private PatronBannerRepository repository;


	@Override
	public boolean authorise(final Request<Banner> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Banner> request, final Banner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Banner> request, final Banner entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "picture", "slogan", "url");
	}

	@Override
	public Banner instantiate(final Request<Banner> request) {
		assert request != null;

		Banner result;

		result = new Banner();
		result.setPatron(this.repository.findOnePatronByUserAccountId(request.getPrincipal().getAccountId()));

		return result;
	}

	@Override
	public void validate(final Request<Banner> request, final Banner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean sloganIsSpam = this.spamChecker(entity.getSlogan());

		if (!errors.hasErrors("slogan")) {
			errors.state(request, !sloganIsSpam, "slogan", "patron.banner.error.spam");
		}

	}

	@Override
	public void create(final Request<Banner> request, final Banner entity) {
		assert request != null;
		assert entity != null;

		entity.setPatron(this.repository.findOnePatronByUserAccountId(request.getPrincipal().getAccountId()));

		this.repository.save(entity);
	}

	private boolean spamChecker(final String str) {
		String strFormatted = str.toLowerCase().trim().replaceAll("\\s+", " ");

		CustomizationParameter cp = this.repository.findCustomizationParameters();
		Double spamThreshold = cp.getSpamThreshold();
		Set<String> spamWords = new HashSet<>();
		spamWords.addAll(Arrays.asList(cp.getSpamWordsEnglish().toString().split(", ")));
		spamWords.addAll(Arrays.asList(cp.getSpamWordsSpanish().toString().split(", ")));

		int spamWordsCounter = 0;

		boolean isSpam = false;

		for (String word : spamWords) {
			if (strFormatted.contains(word)) {
				int i = 0;
				while ((i = str.indexOf(word, i)) != -1) {
					spamWordsCounter++;
					i++;

				}
			}
		}

		if (spamWordsCounter > 0) {
			double spamPercentage = Double.valueOf(spamWordsCounter) / strFormatted.split(" ").length * 100;
			isSpam = spamPercentage >= spamThreshold;
		}

		return isSpam;
	}
}
