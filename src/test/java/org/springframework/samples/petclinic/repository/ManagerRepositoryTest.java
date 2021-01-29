package org.springframework.samples.petclinic.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ManagerRepositoryTest {

	@Autowired
	ManagerRepository managerRepository;
	
	@Test
	public void shouldReturnManagerById() throws Exception {

		Manager manager = this.managerRepository.findById(1);

		assertThat(manager.getFirstName()).isEqualTo("Herrera");

	}
	
	@Test
	public void shouldReturnManagerByUserName() throws Exception {

		Manager manager = this.managerRepository.findByUserName("manager1");

		assertThat(manager.getFirstName()).isEqualTo("Herrera");

	}
	
	
}
