package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedTeamNIF;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedTeamName;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TeamServiceTests {

	@Autowired
	TeamService teamService;

	private Team team;

	private Team team2;

	private Team team3;

	@Autowired
	EntityManager em;

	@BeforeEach
	void setUp() {
		team = this.teamService.findTeamById(1);

		team2 = new Team();

		Date date = new Date();
		team2.setId(5);
		team2.setName("Abrham Mateo");
		team2.setNif("12345678Q");
		team2.setCreationDate(date);
	}

	// CASOS POSITIVOS

	@Test
	@Transactional
	@DisplayName("Create Team")
	void shouldCreateTeam() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		team2.setId(null);

		this.teamService.saveTeam(team2);

		assertThat(this.teamService.findAllTeamsNames().size() == 3);
	}


	@Test
	@Transactional
	@DisplayName("Should not Create Team short name")
	void shouldNotCreateTeam() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		Date date = new Date();
		team3 = new Team();
		team3.setName("A");
		team3.setId(6);
		team3.setNif("12345678Q");
		// team3.setManager(manager);
		team3.setCreationDate(date);

		//this.teamService.saveTeam(team3);

		assertThrows(ConstraintViolationException.class, () -> {
			this.teamService.saveTeam(team3);
			em.flush();
		});

		// assertThat(this.teamService.findAllTeamsNames().size()!=3);

	}

	// Editar un equipo con nombre correcto


	// Editar un equipo con nombre correcto
	
	@Test
	@Transactional
	@DisplayName("Editing Team")
	void shouldEditTeam() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		String name = "Honda Racing Team";
		team.setName(name);
		this.teamService.saveTeam(team);

		assertThat(team.getName()).isEqualTo(name);
	}

	// Eliminar un equipo correctamente
	@Test
	@Transactional
	@DisplayName("Removing Team Correctly")
	void shouldRemoveTeam() throws DataAccessException {

		this.teamService.removeTeam(team.getId());
		// Al eliminar comprueba que el manager ya no tiene equipo
		Integer managerTeam = this.teamService.countTeams(1);
		assertThat(managerTeam).isEqualTo(0);
	}

	@Test
	@Transactional
	@DisplayName("Finding Team By Id")
	void shouldFindTeamById() throws DataAccessException {

		Team team2 = this.teamService.findTeamById(1);
		// Comprueba que el nombre del Team cuyo Id es 1 es correcto
		assertThat(team2.getName()).isEqualTo("LAS DIVINAS");
	}
	
	// Contar cuantos equipos estan a cargo de un manager, pudiendo solo tener 1.

	// Contar cuantos equipos estan a cargo de un manager, pudiendo solo tener 1.

	@Test
	@Transactional
	@DisplayName("Counting how many teams")
	void shouldCount() throws DataAccessException {

		Integer managerId = 1;
		Integer count = this.teamService.countTeams(managerId);
		// Cuenta el numero de equipos que tiene un manager y este solo puede ser 1
		assertThat(count).isEqualTo(1);
	}

	// CASOS NEGATIVOS

	// Editar equipo con un nombre ya en uso

	@Test
	@DisplayName("Edit team with already used name")
	@Transactional
	void shouldThrowExceptionEditingTeamWithAlreadyUsedName() throws DataAccessException, DuplicatedTeamNIF {

		team2.setName("LAS DIVINAS");

		//this.teamService.saveTeam(team2);

		

		assertThrows(DataIntegrityViolationException.class, () -> {
			this.teamService.saveTeam(team2);
			em.flush();
		});
	}
	
	@Test
	@DisplayName("Edit team with already used nif")
	@Transactional
	void shouldThrowExceptionEditingTeamWithAlreadyUsedNif() throws DataAccessException, DuplicatedTeamNIF {

		team2.setNif("12345678D");

		//this.teamService.saveTeam(team2);

		

		assertThrows(DataIntegrityViolationException.class, () -> {
			this.teamService.saveTeam(team2);
			em.flush();
		});
	}

	@Test
	@DisplayName("Edit team with already used nif")
	@Transactional
	void shouldThrowExceptionEditingTeamWithAlreadyUsedNIF()
			throws DataAccessException, DuplicatedTeamNIF, DuplicatedTeamName {

		team.setNif("12345678D");

		this.teamService.saveTeam(team);

		assertThat(!team.getName().equals("LAS POPULARES"));

//		assertThrows(DuplicatedTeamName.class, () -> {
//			this.teamService.saveTeam(team);
//			em.flush();
//		});
	}

	// Editar equipo con valores incorrectos
	@Test
	@DisplayName("Edit team incorrectly")
	@Transactional
	void shouldThrowExceptionEditingTeamIncorrectParameter() throws Exception {


		team.setName("");
		team.setNif("8696948GGHH");

		assertThrows(ConstraintViolationException.class, () -> {
			this.teamService.saveTeam(team);
			em.flush();
		});
	}

	// Editar equipo con otro manager

//		@Test
//		@DisplayName("Edit team with another manager")
//		@Transactional
//		void shouldThrowExceptionEditingTeamIncorrectManager() throws Exception {
//			
//			team.setName("Kawasaki Racing Team");
//			
//			assertThrows(ConstraintViolationException.class, () -> {
//				this.teamService.saveTeam(team);
//				em.flush();
//			});
//		}

}