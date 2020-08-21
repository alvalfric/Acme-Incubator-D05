
package acme.entities.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import acme.framework.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Patron extends UserRole {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	private String				organizationName;

	private String				holderName;

	private String				number;

	private String				brand;

	private String				expirationDate;

	private String				CVV;
}
