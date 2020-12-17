package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "grandPrixes")

public class GrandPrix {
	
	@Column(name = "location")
	@NotEmpty
	private String location;
	
	@Column(name = "circuit")
	@NotEmpty
	private String circuit;
	
	@Column(name = "laps")
	@Range(min = 0, max = 30)
	private Integer laps;
	
	@Column(name = "dayOfRace")
	@NotNull
	@DateTimeFormat(pattern="yyyy/mm/dd")
	private LocalDate dayOfRace;
	
	@Column(name = "endOfRace")
	@NotNull
	@DateTimeFormat(pattern="yyyy/mm/dd'T'HH:mm:ss")
	private LocalDateTime endOfRace;
	
	@Column(name = "distance")
	@Range(min = 95, max = 130)
	private Double distance;
	

}
