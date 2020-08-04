
package acme.features.entrepeneur.activity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.investmentRounds.Activity;
import acme.entities.roles.Entrepeneur;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/entrepeneur/activity/")
public class EntrepeneurActivityController extends AbstractController<Entrepeneur, Activity> {

	@Autowired
	private EntrepeneurActivityShowService		showService;

	@Autowired
	private EntrepeneurActivityCreateService	createService;

	@Autowired
	private EntrepeneurActivityUpdateService	updateService;

	@Autowired
	private EntrepeneurActivityDeleteService	deleteService;


	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
		super.addBasicCommand(BasicCommand.DELETE, this.deleteService);
	}
}
