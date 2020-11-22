package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "motorcycles")
public class Motorcycle extends NamedEntity{
	
	@Column(name = "brand")
	@NotEmpty
	private String brand;
	
	@Column(name = "displacement")
	@NotEmpty
	private Integer displacement;
	
	@Column(name = "horse_power")
	@NotEmpty
	private Integer horsePower;
	
	@Column(name = "weight")
	@NotEmpty
	private Integer weight;
	
	@Column(name = "tank_capacity")
	@NotEmpty
	private Double tankCapacity;
	
	@Column(name = "max_speed")
	@NotEmpty
	private Double maxSpeed;
	
}
