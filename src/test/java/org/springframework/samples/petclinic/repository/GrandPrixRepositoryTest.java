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
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Position;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class GrandPrixRepositoryTest {

	
	@Autowired
	GrandPrixRepository grandPrixRepository;
	
	@Test
	public void shouldFindAllPositionsByGrandPrixId() throws Exception {

		Collection<Position> positions = this.grandPrixRepository.findAllPositions(1);

		assertThat(positions.size()).isEqualTo(2);

	}
	
	@Test
	public void shouldFindAllPilotssByGrandPrixId() throws Exception {

		Collection<Pilot> pilots = this.grandPrixRepository.findAllPilots(1);

		assertThat(pilots.size()).isEqualTo(2);

	}
	
	@Test
	public void shouldFindGrandPrixById() throws Exception {

		GrandPrix grandPrix = this.grandPrixRepository.findGPById(1);

		assertThat(grandPrix.getLocation()).isEqualTo("Finlandia");

	}
	
	
}
