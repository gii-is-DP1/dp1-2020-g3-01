package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "motorcycles")
public class Motorcycle extends BaseEntity {

	@Column(name = "brand")
	@Size(min = 3, max =50)
	private String brand;

	@Column(name = "displacement")
	@NotNull
	@Range(min=125, max=2000)
	private Integer displacement;

	@Column(name = "horse_power")
	@NotNull
	@Range(min=40, max=400)
	private Integer horsePower;

	@Column(name = "weight")
	@NotNull
	@Range(min=0, max=250)
	private Integer weight;

	@Column(name = "tank_capacity")
	@NotNull
	@Range(min=0, max=22)
	private Double tankCapacity;

	@Column(name = "max_speed")
	@NotNull
	@Range(min=120, max=380)
	private Double maxSpeed;

	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	Pilot pilot;

}
