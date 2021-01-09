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
	@Range(min=0, max=2000)
	private Integer pos;
	
	@Column(name = "point")
	@Range(min=0, max=2000)
	private Integer point;

}
