
package acme.entities.toolRecords;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "activitySector"), @Index(columnList = "sourceType")
})
public class ToolRecord extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	private String				title;

	@NotBlank
	private String				activitySector;

	@NotBlank
	private String				inventorName;

	@NotBlank
	private String				description;

	@NotBlank
	@URL
	private String				website;

	@NotBlank
	@Email
	private String				email;

	@NotBlank
	private String				sourceType;

	@Range(min = -5, max = 5)
	private Integer				stars;


	@Transient
	public String getRating() {
		StringBuilder result;

		result = new StringBuilder();
		result.append(this.stars);
		result.append(" ");
		switch (this.stars) {
		case -5:
		case -4:
			result.append("(very bad)");
			break;
		case -3:
		case -2:
		case -1:
			result.append("(bad)");
			break;
		case 0:
			result.append("(okay)");
			break;
		case 1:
		case 2:
			result.append("(good)");
			break;
		case 3:
		case 4:
			result.append("(great)");
			break;
		case 5:
			result.append("(excellent)");
			break;
		}

		return result.toString();

	}
}
