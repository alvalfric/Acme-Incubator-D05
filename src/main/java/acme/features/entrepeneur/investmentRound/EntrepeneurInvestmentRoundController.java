
package acme.features.entrepeneur.investmentRound;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepeneur;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/entrepeneur/investment-round/")
public class EntrepeneurInvestmentRoundController extends AbstractController<Entrepeneur, InvestmentRound> {

	@Autowired
	private EntrepeneurInvestmentRoundListService	listService;

	@Autowired
	private EntrepeneurInvestmentRoundShowService	showService;


	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
	}
}
