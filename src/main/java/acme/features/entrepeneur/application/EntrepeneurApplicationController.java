
package acme.features.entrepeneur.application;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.investmentRounds.Application;
import acme.entities.roles.Entrepeneur;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/entrepeneur/application/")
public class EntrepeneurApplicationController extends AbstractController<Entrepeneur, Application> {

	@Autowired
	private EntrepeneurApplicationListService			listService;

	@Autowired
	private EntrepeneurApplicationListTickerService		listTickerService;

	@Autowired
	private EntrepeneurApplicationListCreationService	listCreationService;

	@Autowired
	private EntrepeneurApplicationShowService			showService;

	@Autowired
	private EntrepeneurApplicationAcceptService			acceptService;

	@Autowired
	private EntrepeneurApplicationRejectService			rejectService;


	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addCustomCommand(CustomCommand.ACCEPT, BasicCommand.UPDATE, this.acceptService);
		super.addCustomCommand(CustomCommand.REJECT, BasicCommand.UPDATE, this.rejectService);
		super.addCustomCommand(CustomCommand.LIST_TICKER, BasicCommand.LIST, this.listTickerService);
		super.addCustomCommand(CustomCommand.LIST_CREATION, BasicCommand.LIST, this.listCreationService);
	}
}
