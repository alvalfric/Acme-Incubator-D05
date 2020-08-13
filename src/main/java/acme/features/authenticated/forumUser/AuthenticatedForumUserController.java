
package acme.features.authenticated.forumUser;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.forums.ForumUser;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Authenticated;

@Controller
@RequestMapping("/authenticated/forum-user/")
public class AuthenticatedForumUserController extends AbstractController<Authenticated, ForumUser> {

	@Autowired
	private AuthenticatedForumUserShowService	showService;

	@Autowired
	private AuthenticatedForumUserAddService	addService;

	@Autowired
	private AuthenticatedForumUserDeleteService	deleteService;


	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addCustomCommand(CustomCommand.ADD, BasicCommand.CREATE, this.addService);
		super.addBasicCommand(BasicCommand.DELETE, this.deleteService);
	}

}
