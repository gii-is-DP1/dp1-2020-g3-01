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
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MechanicRepositoryTest {

	@Autowired
	MechanicRepository mechanicRepository;

	@Test
	public void shouldReturnMechanicById() throws Exception {

		Mechanic mechanic = this.mechanicRepository.findById(1);

		assertThat(mechanic.getFirstName()).isEqualTo("Cesar");

	}
	
	@Test
	public void shouldReturnMechanicByUserName() throws Exception {

		Mechanic mechanic = this.mechanicRepository.findByUserName("mechanic1");

		assertThat(mechanic.getFirstName()).isEqualTo("Cesar");

	}
	
	@Test
	public void shouldReturnAllMechanic() throws Exception {

		Collection<Mechanic> mechanics = this.mechanicRepository.findAll();

		assertThat(mechanics.size()).isEqualTo(2);

	}


}
