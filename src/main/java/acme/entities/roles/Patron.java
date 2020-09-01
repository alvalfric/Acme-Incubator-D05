
package acme.entities.roles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

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

	@Column(length = 60)
	@Length(max = 60)
	private String				holderName;

	@Column(length = 16)
	@Length(max = 16)
	private String				number;

	@Column(length = 60)
	@Length(max = 60)
	private String				brand;

	@Column(length = 7)
	@Length(max = 7)
	private String				expirationDate;

	@Column(length = 4)
	@Length(max = 4)
	private String				CVV;
}
