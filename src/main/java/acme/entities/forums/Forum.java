
package acme.entities.forums;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.entities.Authenticated;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Forum extends DomainEntity {

	private static final long					serialVersionUID	= 1L;

	@NotNull
	private String								forumTitle;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "forum")
	private Set<@Valid ForumMessage>			forumMessages;

	@NotNull
	@OneToOne(optional = false)
	@Valid
	private InvestmentRound						investmentRound;

	@NotNull
	@OneToMany
	private Collection<@Valid Authenticated>	users;

}
