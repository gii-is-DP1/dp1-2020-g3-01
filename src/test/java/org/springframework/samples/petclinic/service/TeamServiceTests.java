package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TeamServiceTests {

	@Autowired
	TeamService teamService;

	private Team team;

	@BeforeEach
	void setUp() {
		team = this.teamService.findTeamById(1);
	}

	// CASOS POSITIVOS

	// Editar un equipo con nombre correcto
	
	@Test
	@Transactional
	@DisplayName("Editing Team")
	void shouldEditTeam() throws DataAccessException {

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

	
	// Encontrar un equipo por su ID
	
	@Test
	@Transactional
	@DisplayName("Finding Team By Id")
	void shouldFindTeamById() throws DataAccessException {

		Team team2 = this.teamService.findTeamById(1);
		// Comprueba que el nombre del Team cuyo Id es 1 es correcto
		assertThat(team2.getName()).isEqualTo("LAS DIVINAS");
	}
	
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
	void shouldThrowExceptionEditingTeamWithAlreadyUsedName() throws Exception {
		
		team.setName("LAS POPULARES");
		
		assertThrows(ConstraintViolationException.class, () -> {
			this.teamService.saveTeam(team);
		});
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
		});
	}
	
	// Editar equipo con otro manager
	
		@Test
		@DisplayName("Edit team with another manager")
		@Transactional
		void shouldThrowExceptionEditingTeamIncorrectManager() throws Exception {
			
			team.setName("Kawasaki Racing Team");
			
			assertThrows(ConstraintViolationException.class, () -> {
				this.teamService.saveTeam(team);
			});
		}
	
}