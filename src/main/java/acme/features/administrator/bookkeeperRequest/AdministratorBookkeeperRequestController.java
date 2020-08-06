/*
 * AuthenticatedConsumerController.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.bookkeeperRequest;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.roles.BookkeeperRequest;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Administrator;

@Controller
@RequestMapping("/administrator/bookkeeper-request/")
public class AdministratorBookkeeperRequestController extends AbstractController<Administrator, BookkeeperRequest> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorBookkeeperRequestShowService	showService;

	@Autowired
	private AdministratorBookkeeperRequestListService	listService;

	@Autowired
	private AdministratorBookkeeperRequestAcceptService	acceptService;


	// Constructors -----------------------------------------------------------

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addCustomCommand(CustomCommand.ACCEPT, BasicCommand.UPDATE, this.acceptService);
	}

}
