package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Positions")

public class Position {
	
	@Column(name = "startingPosition")
	private Integer startingPosition;
	
	@Column(name = "finalPosition")
	private Integer finalPosition;
	
	@Column(name = "points")
	private Integer points;

}
