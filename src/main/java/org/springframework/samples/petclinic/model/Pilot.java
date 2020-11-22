package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "pilots")
public class Pilot extends Person {
	
	@Column(name = "number")
	private Integer number;
	
	@Column(name = "height")
	private Double height;
	
	@Column(name = "weight")
	private Double weight;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
	
	@OneToOne(cascade = CascadeType.ALL)
	Motorcycle motorcycle;
}
