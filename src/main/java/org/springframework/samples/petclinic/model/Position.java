package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "positions")
public class Position extends BaseEntity{
	
	@ManyToOne()
	private Pilot pilot;
	
	@Column(name = "pos")
	@NotNull
	@Range(min=1, max=20)
	private Integer pos;
	
	@Column(name = "point")
	private Integer point;

}
