package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.samples.petclinic.model.Type;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MechanicServiceTest {

	@Autowired
	protected MechanicService mechanicService;
	
	private Mechanic mechanic;
	private User user;
	
   	@BeforeEach
	void setup() {

   		
		mechanic = new Mechanic();
		mechanic.setFirstName("Penelope");
		mechanic.setLastName("Cruz");
		LocalDate date = LocalDate.of(1966, 1, 1);
		mechanic.setBirthDate(date);
		mechanic.setDni("12345678Q");
		mechanic.setId(5);
		mechanic.setNationality("Spain");
		mechanic.setResidence("Spain");
		mechanic.setType(Type.ENGINE);
		user.setUsername("mechanic5");
		user.setPassword("m3ch4n1c5");
		mechanic.setUser(user);
		


	}   
	
	@Test
	@Transactional
	void shouldInsertdNewTournament() throws DataAccessException {
				
		this.mechanicService.saveMechanic(mechanic);
		Collection<Mechanic> mechanics = this.mechanicService.findAllMechanic();
		assertThat(mechanics.size()).isEqualTo(11);
	}

}
