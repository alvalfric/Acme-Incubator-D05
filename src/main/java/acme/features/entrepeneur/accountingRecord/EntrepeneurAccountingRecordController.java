
package acme.features.entrepeneur.accountingRecord;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.roles.Entrepeneur;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/entrepeneur/accounting-record/")
public class EntrepeneurAccountingRecordController extends AbstractController<Entrepeneur, AccountingRecord> {

	@Autowired
	private EntrepeneurAccountingRecordListService	listService;

	@Autowired
	private EntrepeneurAccountingRecordShowService	showService;


	@PostConstruct
	private void initialise() {
		super.addCustomCommand(CustomCommand.LIST_MINE, BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
	}
}
