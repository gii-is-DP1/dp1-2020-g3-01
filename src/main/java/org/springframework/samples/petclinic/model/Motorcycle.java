package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "motorcycles")
public class Motorcycle extends BaseEntity {

	@Column(name = "brand")
	@NotEmpty
	private String brand;

	@Column(name = "displacement")
	@Range(min=0, max=2000)
	private Integer displacement;

	@Column(name = "horse_power")
	@Range(min=0, max=400)
	private Integer horsePower;

	@Column(name = "weight")
	@Range(min=0, max=250)
	private Integer weight;

	@Column(name = "tank_capacity")
	@Range(min=0, max=22)
	private Double tankCapacity;

	@Column(name = "max_speed")
	@Range(min=0, max=380)
	private Double maxSpeed;

	@OneToOne
	Pilot pilot;

}
