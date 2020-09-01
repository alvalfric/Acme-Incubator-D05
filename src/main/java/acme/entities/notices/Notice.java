
package acme.entities.notices;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "deadline")
})
public class Notice extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@URL
	@Column(length = 2048)
	@Length(max = 2048)
	private String				headerPicture;

	@NotBlank
	@Column(length = 256)
	@Length(max = 256)
	private String				title;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	private Date				creation;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@FutureOrPresent
	private Date				deadline;

	@NotBlank
	@Column(length = 2048)
	@Length(max = 2048)
	private String				body;

	@URL
	@Column(length = 2048)
	@Length(max = 2048)
	private String				relatedLink1;

	@URL
	@Column(length = 2048)
	@Length(max = 2048)
	private String				relatedLink2;
}
