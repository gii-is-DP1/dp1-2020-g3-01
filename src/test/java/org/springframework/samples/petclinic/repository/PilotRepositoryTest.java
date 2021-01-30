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
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PilotRepositoryTest {

	
	@Autowired
	PilotRepository pilotRepository;
	
	@Test
	public void shouldFindPilotById() throws Exception {

		Pilot pilot = this.pilotRepository.findById(1);

		assertThat(pilot.getFirstName()).isEqualTo("Valentino");

	}
	
	@Test
	public void shouldFindPilotByUsername() throws Exception {

		Pilot pilot = this.pilotRepository.findByUsername("pilot1");

		assertThat(pilot.getFirstName()).isEqualTo("Valentino");

	}
	
	@Test
	public void shouldFindAllPilots() throws Exception {

		Collection<Pilot> pilots = this.pilotRepository.findAllPilots();

		assertThat(pilots.size()).isEqualTo(2);

	}
	
	
	
}
