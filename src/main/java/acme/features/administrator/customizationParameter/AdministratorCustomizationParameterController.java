
package acme.features.administrator.customizationParameter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.customizationParameters.CustomizationParameter;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Administrator;

@Controller
@RequestMapping("/administrator/customization-parameter/")
public class AdministratorCustomizationParameterController extends AbstractController<Administrator, CustomizationParameter> {

	@Autowired
	private AdministratorCustomizationParameterShowService		showService;

	@Autowired
	private AdministratorCustomizationParameterUpdateService	updateService;


	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
	}
}
