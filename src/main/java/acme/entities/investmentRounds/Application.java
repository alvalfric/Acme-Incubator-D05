
package acme.entities.investmentRounds;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.entities.roles.Investor;
import acme.framework.datatypes.Money;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "ticker"), @Index(columnList = "creation"), @Index(columnList = "status"), @Index(columnList = "creation, status")
})
public class Application extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Pattern(regexp = "^([A-Z]{3}-\\d{2}-\\d{6})$")
	private String				ticker;

	@NotNull
	@PastOrPresent
	private Date				creation;

	@NotBlank
	@Column(length = 4096)
	@Length(max = 4096)
	private String				statement;

	@NotNull
	private Money				offer;

	@NotNull
	@Column(length = 256)
	@Length(max = 256)
	private String				status;

	private String				rejectJustification;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private InvestmentRound		investmentRound;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Investor			investor;
}
