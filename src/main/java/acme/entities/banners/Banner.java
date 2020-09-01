
package acme.entities.banners;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.roles.Patron;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@URL
	@Column(length = 2048)
	@Length(max = 2048)
	private String				picture;

	@NotBlank
	@Column(length = 256)
	@Length(max = 256)
	private String				slogan;

	@NotBlank
	@URL
	@Column(length = 2048)
	@Length(max = 2048)
	private String				url;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Patron				patron;
}
