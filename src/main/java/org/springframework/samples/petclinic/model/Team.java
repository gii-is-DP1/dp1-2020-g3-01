package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "teams")
public class Team extends NamedEntity{

	@Column(name =  "name", unique=true)
	@NotEmpty
	private String name;
	
	@Column(name =  "creationDate")
	private Date creationDate;
	
	@Pattern(regexp = "\\d{8}[A-HJ-NP-TV-Z]")
	@Column(name =  "nif", unique=true)
	@NotEmpty
	private String nif;
	
	@OneToOne(cascade = CascadeType.ALL)
	Manager manager;
	
	
}
