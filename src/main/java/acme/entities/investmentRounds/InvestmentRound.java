
package acme.entities.investmentRounds;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.entities.roles.Entrepeneur;
import acme.framework.datatypes.Money;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "ticker"), @Index(columnList = "finalMode"), @Index(columnList = "round")
})
public class InvestmentRound extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Pattern(regexp = "^([A-Z]{3}-\\d{2}-\\d{6})$")
	private String				ticker;

	@NotNull
	@PastOrPresent
	private Date				creation;

	@NotBlank
	private String				round;

	@NotBlank
	private String				title;

	@NotBlank
	private String				description;

	@NotNull
	private Money				amount;

	@URL
	private String				link;

	@NotNull
	private boolean				finalMode;

	//	//@NotNull
	//	@OneToMany(fetch = FetchType.EAGER, mappedBy = "investmentRound")
	//	private Set<@Valid Activity>			workProgramme;
	//
	//	@OneToMany(fetch = FetchType.EAGER, mappedBy = "investmentRound")
	//	private Set<@Valid Application>			applications;
	//
	//	@OneToOne(optional = false, mappedBy = "investmentRound", cascade = CascadeType.ALL)
	//	private Forum							forum;
	//
	//	@OneToMany(fetch = FetchType.EAGER, mappedBy = "investmentRound")
	//	private Set<@Valid AccountingRecord>	accountingRecords;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Entrepeneur			entrepeneur;
}
