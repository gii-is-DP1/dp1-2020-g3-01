package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "pilots")
public class Pilot extends Person {
	
	@Column(name = "number", unique=true)
	@NotNull
	@Range(min=0, max=99)
	private Integer number;
	
	@Column(name = "height")
	@Range(min=0, max=2)
	@NotNull
	private Double height;
	
	@Column(name = "weight")
	@Range(min=0, max=100)
	@NotNull
	private Double weight;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	@NotNull
	private User user;
	
//	@ManyToOne()
//	private Team team;
	
}
