
package acme.entities.customizationParameters;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;
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
	@Column(length = 4096)
	@Length(max = 4096)
	private String				spamWordsEnglish;

	@NotBlank
	@Column(length = 4096)
	@Length(max = 4096)
	private String				spamWordsSpanish;

	@NotNull
	@PositiveOrZero
	@Range(min = 0, max = 100)
	private Double				spamThreshold;

	@NotBlank
	@Column(length = 4096)
	@Length(max = 4096)
	private String				activitySectors;
}
