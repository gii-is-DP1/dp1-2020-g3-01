package org.springframework.samples.petclinic.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "messages")
public class Message extends BaseEntity {

	@Column(name = "message")
	@NotEmpty
	private String message;

	@Column(name = "creationDate")
	private Date creationDate;

	@OneToOne(cascade = CascadeType.ALL)
	Attachment Attachment;
	
}
