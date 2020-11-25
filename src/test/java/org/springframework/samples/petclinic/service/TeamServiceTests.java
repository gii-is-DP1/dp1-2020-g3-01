package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

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

	// Casos positivos

	@Test
	@Transactional
	@DisplayName("Editing Team")
	void shouldEditTeam() throws DataAccessException {

		String name = "Honda Racing Team";
		team.setName(name);
		this.teamService.saveTeam(team);

		assertThat(team.getName()).isEqualTo(name);
	}

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

	@Test
	@Transactional
	@DisplayName("Counting how many teams")
	void shouldCount() throws DataAccessException {

		Integer managerId = 1;
		Integer count = this.teamService.countTeams(managerId);
		// Cuenta el numero de equipos que tiene un manager y este solo puede ser 1
		assertThat(count).isEqualTo(1);
	}

//	// Casos negativos
//	@Test
//	@DisplayName("find by id doesn't exists ")
//	void testFindbybadId() throws DataAccessException {
//		int badId = 3484;
//		when(repo.findById(badId)).thenThrow(DataAccessException.class);
//		assertThrows(DataAccessException.class, () -> scoreService.findScoreById(badId));
//	}

}
