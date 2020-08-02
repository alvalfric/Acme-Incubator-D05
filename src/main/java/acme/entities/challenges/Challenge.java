
package acme.entities.challenges;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "deadline")
})
public class Challenge extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	private String				title;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Future
	private Date				deadline;

	@NotBlank
	private String				description;

	@NotBlank
	private String				rookieGoal;

	@NotBlank
	private String				averageGoal;

	@NotBlank
	private String				expertGoal;

	@NotBlank
	private String				rookieReward;

	@NotBlank
	private String				averageReward;

	@NotBlank
	private String				expertReward;


	@Transient
	public String getRookieGoalReward() {
		StringBuilder result;

		result = new StringBuilder();
		result.append(this.rookieGoal);
		result.append(" / ");
		result.append(this.rookieReward);

		return result.toString();

	}

	@Transient
	public String getAverageGoalReward() {
		StringBuilder result;

		result = new StringBuilder();
		result.append(this.averageGoal);
		result.append(" / ");
		result.append(this.averageReward);

		return result.toString();

	}

	@Transient
	public String getExpertGoalReward() {
		StringBuilder result;

		result = new StringBuilder();
		result.append(this.expertGoal);
		result.append(" / ");
		result.append(this.expertReward);

		return result.toString();

	}
}
