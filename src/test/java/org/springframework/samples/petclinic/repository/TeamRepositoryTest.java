package org.springframework.samples.petclinic.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TeamRepositoryTest {
	
	@Autowired
	TeamRepository teamRepository;
	
	@Test
	public void shouldCountOnlyOne() throws Exception {

		Integer count = this.teamRepository.countTeams(1);

		assertThat(count).isEqualTo(1);

	}
	
	@Test
	public void shouldFinAllMechanicOfTeam() throws Exception {

		Collection<Mechanic> mechanics = this.teamRepository.getMechanicsById(1);

		assertThat(mechanics.size()).isEqualTo(2);

	}
	
	@Test
	public void shouldFindTeamById() throws Exception {

		Team team= this.teamRepository.findTeamById(1);

		assertThat(team.getName()).isEqualTo("LAS DIVINAS");

	}
	
	@Test
	public void shouldFindTeamFromManagerById() throws Exception {

		Team team= this.teamRepository.findManager(1);

		assertThat(team.getName()).isEqualTo("LAS DIVINAS");

	}


}
