
package acme.entities.creditCards;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CreditCard extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	private String				holderName;

	@NotBlank
	private String				number;

	@NotBlank
	private String				brand;

	@NotBlank
	private String				expirationDate;

	@NotBlank
	private String				CVV;
}
