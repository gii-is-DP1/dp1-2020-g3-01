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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "teams")
public class Team extends BaseEntity{

	@Column(name =  "name", unique=true)
	@NotEmpty
	@Size(min = 3, max =50)
	private String name;
	
	@Column(name =  "creationDate")
	private Date creationDate;
	
	@Pattern(regexp = "\\d{8}[A-HJ-NP-TV-Z]")
	@Column(name =  "nif", unique=true)
	@NotEmpty
	private String nif;
	
	@OneToOne(cascade = CascadeType.ALL)
	Manager manager;
	
	@OneToMany(cascade = CascadeType.ALL)
	Set<Pilot> pilot;
	
	@OneToMany(cascade = CascadeType.ALL)
	Set<Mechanic> mechanic;
	
}
