
package acme.features.administrator.inquirie;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.inquiries.Inquirie;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Administrator;

@Controller
@RequestMapping("/administrator/inquirie/")
public class AdministratorInquireController extends AbstractController<Administrator, Inquirie> {

	@Autowired
	private AdministratorInquireListService		listService;

	@Autowired
	private AdministratorInquireShowService		showService;

	@Autowired
	private AdministratorInquirieCreateService	createService;

	@Autowired
	private AdministratorInquirieUpdateService	updateService;

	@Autowired
	private AdministratorInquirieDeleteService	deleteService;


	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
		super.addBasicCommand(BasicCommand.DELETE, this.deleteService);
	}
}
