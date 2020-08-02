
package acme.entities.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import acme.framework.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Entrepeneur extends UserRole {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	private String				startUpName;

	@NotBlank
	private String				activitySector;

	@NotBlank
	private String				qualification;

	@NotBlank
	private String				skills;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
