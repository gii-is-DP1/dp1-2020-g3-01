package org.springframework.samples.petclinic.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "threads")
public class Thread extends BaseEntity{
	 
	@Column(name="title")
	@NotEmpty
	private String title;
	
	@Column(name="date")
	private Date creationDate;
	
	@OneToMany(cascade = CascadeType.ALL)
	List<Message> messages;
}
