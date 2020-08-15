
package acme.features.entrepeneur.investmentRound;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.accountingRecords.AccountingRecord;
import acme.entities.forums.Forum;
import acme.entities.forums.ForumUser;
import acme.entities.investmentRounds.Activity;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepeneur;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractDeleteService;

@Service
public class EntrepeneurInvestmentRoundDeleteService implements AbstractDeleteService<Entrepeneur, InvestmentRound> {

	@Autowired
	private EntrepeneurInvestmentRoundRepository repository;


	@Override
	public boolean authorise(final Request<InvestmentRound> request) {
		assert request != null;

		InvestmentRound investment = this.repository.findOneById(request.getModel().getInteger("id"));
		Principal principal = request.getPrincipal();
		boolean canBeDelete = this.repository.findManyAllApplicationsByInvestmentRoundId(request.getModel().getInteger("id")).isEmpty();

		return principal.getAccountId() == investment.getEntrepeneur().getUserAccount().getId() && canBeDelete;
	}

	@Override
	public void bind(final Request<InvestmentRound> request, final InvestmentRound entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<InvestmentRound> request, final InvestmentRound entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("canBeDeleted", this.repository.findManyAllApplicationsByInvestmentRoundId(request.getModel().getInteger("id")).isEmpty());

		request.unbind(entity, model, "ticker", "creation", "round", "title", "description", "amount", "link", "finalMode", "workProgramme");
	}

	@Override
	public InvestmentRound findOne(final Request<InvestmentRound> request) {
		assert request != null;

		InvestmentRound result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}

	@Override
	public void validate(final Request<InvestmentRound> request, final InvestmentRound entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void delete(final Request<InvestmentRound> request, final InvestmentRound entity) {
		assert request != null;
		assert entity != null;

		Collection<Activity> workProgramme = this.repository.findManyAllActivityByInvestmentRoundId(entity.getId());
		Collection<AccountingRecord> accountingRecords = this.repository.findManyAllAccountingRecordsByInvestmentRound(entity);
		Forum forum = this.repository.findOneForumByInvestmentRoundId(entity.getId());

		if (workProgramme != null) {
			this.repository.deleteAll(workProgramme);
		}

		if (accountingRecords != null) {
			this.repository.deleteAll(accountingRecords);
		}

		if (forum != null) {
			Set<ForumUser> forumUsers = this.repository.findManyAllForumUsersByForumId(forum.getId());
			if (forumUsers != null) {
				this.repository.deleteAll(forumUsers);
			}

			this.repository.delete(forum);
		}

		this.repository.delete(entity);
	}

}
