package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "grandPrix")
public class GrandPrix extends BaseEntity {

	@Column(name = "location")
	@NotEmpty
	@Size(min = 3, max = 50)
	private String location;

	@Column(name = "circuit")
	@NotEmpty
	@Size(min = 3, max = 50)
	private String circuit;

	@Column(name = "laps")
	@NotNull
	@Range(min = 0, max = 30)
	private Integer laps;

	@Column(name = "distance")
	@NotNull
	@Range(min = 0, max = 200)
	private Double distance;

	@Column(name = "dayOfRace")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	private LocalDate dayOfRace;

//	@OneToMany
//	@OnDelete(action = OnDeleteAction.CASCADE)
//	Pilot pilot;
//	

	@ManyToMany(cascade = CascadeType.ALL)
	Set<Team> team;

}
