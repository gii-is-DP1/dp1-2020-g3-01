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
import org.springframework.samples.petclinic.model.Motorcycle;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MotorcycleRepositoryTest {

	
	@Autowired
	MotorcycleRepository motorcycleRepository;
	
	
	@Test
	public void shouldCountsTeamsOfPilot() throws Exception {

		Integer bikes = this.motorcycleRepository.countBikes(1);

		assertThat(bikes).isEqualTo(1);

	}
	
	@Test
	public void shouldFindMotorcycleById() throws Exception {

		Motorcycle bike = this.motorcycleRepository.findMotorcycleById(1);

		assertThat(bike.getMaxSpeed()).isEqualTo(180);

	}
	
	@Test
	public void shouldFindAllMotorcycles() throws Exception {

		Collection<Motorcycle> bikes = this.motorcycleRepository.findAll();

		assertThat(bikes.size()).isEqualTo(2);

	}
	
	@Test
	public void shouldFindMotorcycleByPilotId() throws Exception {

		Motorcycle bike = this.motorcycleRepository.findMotorcycleByPilotId(1);

		assertThat(bike.getBrand()).isEqualTo("YAMAHA");

	}
	
	
}
