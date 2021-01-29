package org.springframework.samples.petclinic.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "grandprix")
public class GrandPrix extends BaseEntity {

	@Column(name = "location", unique=true)
	@NotEmpty
	@Size(min = 3, max = 50)
	private String location;

	@Column(name = "circuit", unique=true)
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
	private Date dayOfRace;

	@ManyToMany()
	private Set<Pilot> pilots;
	
	@OneToMany()
	@JoinColumn(name = "position_id")
	private Set<Position> positions;

	@ManyToMany()
	@JoinTable(name = "grandPrix_teams", joinColumns = @JoinColumn(name = "grandPrix_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))
	private Set<Team> team;

}
