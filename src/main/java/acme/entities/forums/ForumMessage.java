
package acme.entities.forums;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;

import acme.framework.entities.Authenticated;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ForumMessage extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(length = 256)
	@Length(max = 256)
	private String				title;

	@NotNull
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date				creation;

	@Column(length = 1024)
	@Length(max = 1024)
	private String				tags;

	@NotBlank
	@Column(length = 4096)
	@Length(max = 4096)
	private String				body;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Forum				forum;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Authenticated		user;
}
