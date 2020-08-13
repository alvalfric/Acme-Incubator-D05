
package acme.entities.customizationParameters;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Range;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CustomizationParameter extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	private String				spamWordsEnglish;

	@NotBlank
	private String				spamWordsSpanish;

	@NotNull
	@PositiveOrZero
	@Range(min = 0, max = 100)
	private Double				spamThreshold;

	@NotBlank
	private String				activitySectors;
}
