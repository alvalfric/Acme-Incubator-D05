
package acme.features.authenticated.patron;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Patron;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedPatronCreateService implements AbstractCreateService<Authenticated, Patron> {

	@Autowired
	private AuthenticatedPatronRepository repository;


	@Override
	public boolean authorise(final Request<Patron> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Patron> request, final Patron entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Patron> request, final Patron entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "organizationName", "holderName", "number", "brand", "expirationDate", "CVV");
	}

	@Override
	public Patron instantiate(final Request<Patron> request) {
		assert request != null;

		Patron result;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		result = new Patron();
		result.setUserAccount(userAccount);

		return result;
	}

	@Override
	public void validate(final Request<Patron> request, final Patron entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean creditCardFieldsEmpty = entity.getHolderName().isEmpty() && entity.getNumber().isEmpty() && entity.getBrand().isEmpty() && entity.getExpirationDate().isEmpty() && entity.getCVV().isEmpty();

		if (!errors.hasErrors("holderName")) {
			if (!creditCardFieldsEmpty) {
				errors.state(request, !entity.getHolderName().isEmpty(), "holderName", "authenticated.patron.error.field.empty");
			}
		}

		if (!errors.hasErrors("number")) {
			String regexCreditCardNumber = "^(?:4[0-9]{12}(?:[0-9]{3})?|(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|6(?:011|5[0-9]{2})[0-9]{12}|(?:2131|1800|35\\d{3})\\d{11})$";
			if (entity.getNumber().isEmpty()) {
				errors.state(request, creditCardFieldsEmpty, "number", "authenticated.banner.error.field.empty");
			} else if (entity.getNumber().matches("^[0-9]+$")) {
				errors.state(request, this.checkLuhnCreditCardNumber(entity.getNumber()) && entity.getNumber().matches(regexCreditCardNumber), "number", "authenticated.patron.error.number.invalid");
			} else {
				errors.state(request, false, "number", "authenticated.patron.error.number.number");
			}
		}

		if (!errors.hasErrors("brand")) {
			if (!creditCardFieldsEmpty) {
				errors.state(request, !entity.getBrand().isEmpty(), "brand", "authenticated.patron.error.field.empty");
			}
		}

		if (!errors.hasErrors("expirationDate")) {

			if (entity.getExpirationDate().isEmpty() && entity.getExpirationDate() != null) {
				errors.state(request, creditCardFieldsEmpty, "expirationDate", "patron.banner.error.field.empty");
			} else if (!entity.getExpirationDate().matches("^(0[1-9]|1[0-2])\\/20([0-9]{2})$")) {

				errors.state(request, false, "expirationDate", "authenticated.patron.error.expirationDate.format");
			} else {
				boolean validExpirationDate = false;

				int year = Calendar.getInstance().get(Calendar.YEAR);
				int month = Calendar.getInstance().get(Calendar.MONTH);

				if (year == Integer.valueOf(entity.getExpirationDate().substring(3))) {
					validExpirationDate = Integer.valueOf(entity.getExpirationDate().substring(0, 2)) > month;
				} else if (Integer.valueOf(entity.getExpirationDate().substring(3)) > year) {
					validExpirationDate = true;
				}

				errors.state(request, validExpirationDate, "expirationDate", "authenticated.patron.error.expirationDate.valid");
			}
		}

		if (!errors.hasErrors("CVV")) {
			if (entity.getCVV().isEmpty() && entity.getCVV() != null) {
				errors.state(request, creditCardFieldsEmpty, "CVV", "authenticated.patron.error.field.empty");
			} else if (entity.getCVV().toString().length() != 3) {
				errors.state(request, false, "CVV", "authenticated.patron.error.cvv.format");
			}
		}
	}

	@Override
	public void create(final Request<Patron> request, final Patron entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

	@Override
	public void onSuccess(final Request<Patron> request, final Response<Patron> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

	private boolean checkLuhnCreditCardNumber(final String ccNumber) {
		int sum = 0;
		boolean alternate = false;
		for (int i = ccNumber.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(ccNumber.substring(i, i + 1));
			if (alternate) {
				n *= 2;
				if (n > 9) {
					n = n % 10 + 1;
				}
			}
			sum += n;
			alternate = !alternate;
		}
		return sum % 10 == 0;
	}
}
