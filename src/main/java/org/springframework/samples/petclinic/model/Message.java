package org.springframework.samples.petclinic.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "messages")
public class Message extends BaseEntity {

	@Column(name = "text")
	@Size(min = 4, max = 2000)
	@NotEmpty
	private String text;

	@Column(name = "creationDate")
	private Date creationDate;
	
//	@OneToOne(cascade = CascadeType.ALL)
//	Attachment Attachment;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="username")
	User user;
	
}
