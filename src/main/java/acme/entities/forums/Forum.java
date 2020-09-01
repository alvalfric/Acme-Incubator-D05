
package acme.entities.forums;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Forum extends DomainEntity {

	private static final long				serialVersionUID	= 1L;

	@NotNull
	@Column(length = 256)
	@Length(max = 256)
	private String							forumTitle;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "forum")
	private Collection<@Valid ForumMessage>	forumMessages;

	@NotNull
	@OneToOne(optional = false)
	@Valid
	private InvestmentRound					investmentRound;

	//	@NotNull
	//	@ManyToMany(fetch = FetchType.EAGER)
	//	private Set<@Valid Authenticated>	users;

}
