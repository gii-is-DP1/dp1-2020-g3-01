package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "motorcycles")
public class Motorcycle extends BaseEntity{
	
	@Column(name = "brand")
	@NotEmpty
	private String brand;
	
	@Column(name = "displacement")
	@NotNull
	private Integer displacement;
	
	@Column(name = "horse_power")
	@NotNull
	private Integer horsePower;
	
	@Column(name = "weight")
	@NotNull
	private Integer weight;
	
	@Column(name = "tank_capacity")
	@NotNull
	private Double tankCapacity;
	
	@Column(name = "max_speed")
	@NotNull
	private Double maxSpeed;
	
	@OneToOne
	Pilot pilot;
	
}
