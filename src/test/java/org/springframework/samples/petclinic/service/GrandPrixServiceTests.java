package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.Motorcycle;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedTeamNIF;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedTeamName;
import org.springframework.samples.petclinic.service.exceptions.MaxTeamsException;
import org.springframework.samples.petclinic.service.exceptions.NoPilotsException;
import org.springframework.samples.petclinic.service.exceptions.PilotWithoutBikeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GrandPrixServiceTests {

	@Autowired
	GrandPrixService grandPrixService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	PilotService pilotService;
	
	@Autowired
	MotorcycleService motorcycleService;
	
	GrandPrix gp;
	
	Team team;

//	private GrandPrix grandPrix;
	@Autowired
	EntityManager em;

	@BeforeEach
	void setUp() {
		gp = this.grandPrixService.findGPById(1);
		team = this.teamService.findTeamById(1);
	}

	// CASOS POSITIVOS

	// Historia de usuario 16 - Listado de todos los gran premios

	@Test
	@Transactional
	@DisplayName("List of all GP")
	void shouldFindAllTournaments() {
		Collection<GrandPrix> gp = this.grandPrixService.findAll();
		assertThat(gp.size()).isEqualTo(1);
	}

	// Añadir gran premio con valores correctos

	@Test
	@Transactional
	@DisplayName("Create GrandPrix with correct values")
	void shouldCreateGrandPrix() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		GrandPrix grandPrix = new GrandPrix();
		grandPrix.setLocation("Le Mans, Francia");
		grandPrix.setCircuit("Circuito de LeMans");
		grandPrix.setDistance(113.0);
		grandPrix.setLaps(27);
		Date dayRace = Date.from(Instant.now());
		grandPrix.setDayOfRace(dayRace);

		assertThat(this.grandPrixService.findAll().contains(grandPrix));
	}

	// Añadir segundo gran premio con valores correctos

	@Test
	@Transactional
	@DisplayName("Create GrandPrix with correct values 2")
	void shouldCreateGrandPrix2() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		GrandPrix grandPrix2 = new GrandPrix();
		grandPrix2.setLocation("Silverstone, Inglaterra");
		grandPrix2.setCircuit("Circuito de Silverstone");
		grandPrix2.setDistance(118.0);
		grandPrix2.setLaps(20);
		Date dayRace = Date.from(Instant.now());
		grandPrix2.setDayOfRace(dayRace);

		assertThat(this.grandPrixService.findAll().contains(grandPrix2));
	}
	
	// Editar gran premio con valores correctos
	
	@Test
	@Transactional
	@DisplayName("Edit GrandPrix with correct values")
	void shouldModifyGrandPrix() throws DataAccessException, NoPilotsException, PilotWithoutBikeException, MaxTeamsException {
		gp.setLaps(7);
		this.grandPrixService.save(gp);
		assertThat(this.grandPrixService.findGPById(1)).isEqualTo(gp);
	}
	
	// Eliminar un gran premio
	
	@Test
	@Transactional
	@DisplayName("Delete GrandPrix")
	void shouldDeleteGrandPrix() throws DataAccessException {
		this.grandPrixService.removeGP(gp);
		Collection<GrandPrix> gps = this.grandPrixService.findAll();
		assertThat(gps.size()).isEqualTo(0);
	}
	
	// Inscribir un equipo a una carrera
	
	@Test
	@Transactional
	@DisplayName("Inscribe a team in a GP")
	void shouldAddTeamInGP() throws DataAccessException, NoPilotsException, PilotWithoutBikeException, MaxTeamsException {
		Set<Team> teams = gp.getTeam();
		teams.add(team);
		gp.setTeam(teams);
		this.grandPrixService.saveGP(gp, team);
		Collection<Team> equipos = this.grandPrixService.findTeamsOfGP(1);
		assertThat(equipos).contains(team);
	}
	
	// Eliminar un equipo de una carrera
	
	@Test
	@Transactional
	@DisplayName("Remove a team from a GP")
	void shouldRemoveTeamInGP() throws DataAccessException, NoPilotsException, PilotWithoutBikeException, MaxTeamsException {
		Set<Team> teams = gp.getTeam();
		teams.remove(team);
		gp.setTeam(teams);
		this.grandPrixService.saveGP(gp, team);
		Collection<Team> equipos = this.grandPrixService.findTeamsOfGP(1);
		assertThat(equipos).doesNotContain(team);
	}

	// CASOS NEGATIVOS

	// Añadir gran premio con localización inferior al numero de caracteres exigido

	@Test
	@Transactional
	@DisplayName("Should not Create GP short location")
	void shouldNotCreateGPShortLocation() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		GrandPrix grandPrix = new GrandPrix();
		grandPrix.setLocation("Si");
		grandPrix.setCircuit("Circuito de Silverstone");
		grandPrix.setDistance(113.0);
		grandPrix.setLaps(27);
		Date dayRace = Date.from(Instant.now());
		grandPrix.setDayOfRace(dayRace);

		assertThrows(ConstraintViolationException.class, () -> {
			this.grandPrixService.save(grandPrix);
			em.flush();
		});
	}

	// Añadir gran premio con circuito inferior al numero de caracteres exigido

	@Test
	@Transactional
	@DisplayName("Should not Create GP short circuit")
	void shouldNotCreateGPShortCircuit() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		GrandPrix grandPrix = new GrandPrix();
		grandPrix.setLocation("Silverstone");
		grandPrix.setCircuit("Ci");
		grandPrix.setDistance(113.0);
		grandPrix.setLaps(27);
		Date dayRace = Date.from(Instant.now());
		grandPrix.setDayOfRace(dayRace);

		assertThrows(ConstraintViolationException.class, () -> {
			this.grandPrixService.save(grandPrix);
			em.flush();
		});
	}

	// Añadir gran premio con numero de vueltas negativo

	@Test
	@Transactional
	@DisplayName("Should not Create GP negative laps")
	void shouldNotCreateGPNegativeLaps() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		GrandPrix grandPrix = new GrandPrix();
		grandPrix.setLocation("Silverstone");
		grandPrix.setCircuit("Circuito de Silverstone");
		grandPrix.setDistance(113.0);
		grandPrix.setLaps(-27);
		Date dayRace = Date.from(Instant.now());
		grandPrix.setDayOfRace(dayRace);

		assertThrows(ConstraintViolationException.class, () -> {
			this.grandPrixService.save(grandPrix);
			em.flush();
		});
	}

	// Añadir gran premio con numero de vueltas maximo al permitido

	@Test
	@Transactional
	@DisplayName("Should not Create GP max laps")
	void shouldNotCreateGPMaxLaps() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		GrandPrix grandPrix = new GrandPrix();
		grandPrix.setLocation("Silverstone");
		grandPrix.setCircuit("Circuito de Silverstone");
		grandPrix.setDistance(113.0);
		grandPrix.setLaps(50);
		Date dayRace = Date.from(Instant.now());
		grandPrix.setDayOfRace(dayRace);

		assertThrows(ConstraintViolationException.class, () -> {
			this.grandPrixService.save(grandPrix);
			em.flush();
		});
	}

	// Añadir gran premio con numero de distancia negativa

	@Test
	@Transactional
	@DisplayName("Should not Create GP negative distance")
	void shouldNotCreateGPNegativeDistance() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		GrandPrix grandPrix = new GrandPrix();
		grandPrix.setLocation("Silverstone");
		grandPrix.setCircuit("Circuito de Silverstone");
		grandPrix.setDistance(-113.0);
		grandPrix.setLaps(10);
		Date dayRace = Date.from(Instant.now());
		grandPrix.setDayOfRace(dayRace);

		assertThrows(ConstraintViolationException.class, () -> {
			this.grandPrixService.save(grandPrix);
			em.flush();
		});
	}

	// Añadir gran premio con numero de vueltas maximo al permitido

	@Test
	@Transactional
	@DisplayName("Should not Create GP max distance")
	void shouldNotCreateGPMaxDistance() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		GrandPrix grandPrix = new GrandPrix();
		grandPrix.setLocation("Silverstone");
		grandPrix.setCircuit("Circuito de Silverstone");
		grandPrix.setDistance(513.0);
		grandPrix.setLaps(20);
		Date dayRace = Date.from(Instant.now());
		grandPrix.setDayOfRace(dayRace);

		assertThrows(ConstraintViolationException.class, () -> {
			this.grandPrixService.save(grandPrix);
			em.flush();
		});
	}
	
	// Editar gran premio con localización inferior al numero de caracteres exigido

	@Test
	@Transactional
	@DisplayName("Should not Edit GP short location")
	void shouldNoteditGPShortLocation() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		gp.setLocation("Si");
		gp.setCircuit("Circuito de Silverstone");
		gp.setDistance(113.0);
		gp.setLaps(27);
		Date dayRace = Date.from(Instant.now());
		gp.setDayOfRace(dayRace);

		assertThrows(ConstraintViolationException.class, () -> {
			this.grandPrixService.save(gp);
			em.flush();
		});
	}

	// Editar gran premio con circuito inferior al numero de caracteres exigido

	@Test
	@Transactional
	@DisplayName("Should not Edit GP short circuit")
	void shouldNotEditGPShortCircuit() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		gp.setLocation("Silverstone");
		gp.setCircuit("Ci");
		gp.setDistance(113.0);
		gp.setLaps(27);
		Date dayRace = Date.from(Instant.now());
		gp.setDayOfRace(dayRace);

		assertThrows(ConstraintViolationException.class, () -> {
			this.grandPrixService.save(gp);
			em.flush();
		});
	}

	// Editar gran premio con numero de vueltas negativo

	@Test
	@Transactional
	@DisplayName("Should not Edit GP negative laps")
	void shouldNotEditGPNegativeLaps() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		gp.setLocation("Silverstone");
		gp.setCircuit("Circuito de Silverstone");
		gp.setDistance(113.0);
		gp.setLaps(-27);
		Date dayRace = Date.from(Instant.now());
		gp.setDayOfRace(dayRace);

		assertThrows(ConstraintViolationException.class, () -> {
			this.grandPrixService.save(gp);
			em.flush();
		});
	}

	// Editar gran premio con numero de vueltas maximo al permitido

	@Test
	@Transactional
	@DisplayName("Should not Edit GP max laps")
	void shouldNotEditGPMaxLaps() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		gp.setLocation("Silverstone");
		gp.setCircuit("Circuito de Silverstone");
		gp.setDistance(113.0);
		gp.setLaps(50);
		Date dayRace = Date.from(Instant.now());
		gp.setDayOfRace(dayRace);

		assertThrows(ConstraintViolationException.class, () -> {
			this.grandPrixService.save(gp);
			em.flush();
		});
	}

	// Editar gran premio con numero de distancia negativa

	@Test
	@Transactional
	@DisplayName("Should not Edit GP negative distance")
	void shouldNotEditGPNegativeDistance() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		gp.setLocation("Silverstone");
		gp.setCircuit("Circuito de Silverstone");
		gp.setDistance(-113.0);
		gp.setLaps(10);
		Date dayRace = Date.from(Instant.now());
		gp.setDayOfRace(dayRace);

		assertThrows(ConstraintViolationException.class, () -> {
			this.grandPrixService.save(gp);
			em.flush();
		});
	}

	// Editar gran premio con numero de vueltas maximo al permitido

	@Test
	@Transactional
	@DisplayName("Should not Edit GP max distance")
	void shouldNotEditGPMaxDistance() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		gp.setLocation("Silverstone");
		gp.setCircuit("Circuito de Silverstone");
		gp.setDistance(513.0);
		gp.setLaps(20);
		Date dayRace = Date.from(Instant.now());
		gp.setDayOfRace(dayRace);

		assertThrows(ConstraintViolationException.class, () -> {
			this.grandPrixService.save(gp);
			em.flush();
		});
	}
	
	// Incribir equipo sin pilotos
	
	@Test
	@Transactional
	@DisplayName("Should not inscribe a Team in a GP (If it doesn't have any Pilots)")
	void shouldNotAddTeamInGPPilots() throws DataAccessException, NoPilotsException {
		
		Set<Pilot> sp = new HashSet<Pilot>();
		team.setPilot(sp);
		
		assertThrows(NoPilotsException.class, () -> { this.grandPrixService.saveGP(gp, team); em.flush();});
		
	}
	
	// Inscribir equipo con un piloto sin moto
	
	@Test
	@Transactional
	@DisplayName("Should not inscribe a Team in a GP (If any Pilot doesn't have a Bike)")
	void shouldNotAddTeamInGPBike() throws DataAccessException, PilotWithoutBikeException {
		
		Set<Pilot> sp = new HashSet<Pilot>();
		Pilot pilot = this.pilotService.findById(1);
		Motorcycle bike = this.motorcycleService.findMotorcycleByPilotId(1);
		sp.add(pilot);
		this.motorcycleService.removeBike(bike.getId());
		
		assertThrows(NullPointerException.class, () -> { this.grandPrixService.saveGP(gp, team); em.flush();});
		
	}
}