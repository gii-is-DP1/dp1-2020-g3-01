package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

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
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.samples.petclinic.model.Type;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPersonDni;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedTeamNIF;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MechanicServiceTest {

	@Autowired
	protected MechanicService mechanicService;
	
	
	private Mechanic mechanic;
	
	@Autowired
	EntityManager em;
	//private User user;
	
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
		User user = new User();
		user.setUsername("mechanic5");
		user.setPassword("m3ch4n1c5");
		user.setEnabled(true);
		mechanic.setUser(user);
		


	}   
   	
	@Test
	@Transactional
	@DisplayName("Find mechanic with Id")
	public void shouldFindMechanicById() throws DataAccessException {
		Mechanic mechanic = this.mechanicService.findMechanicById(1);
		assertThat(mechanic.getFirstName().equals("Cesar"));
	}
	
	@Test
	@Transactional
	@DisplayName("Find all mechanic")
	public void shouldFindAllMechanic() throws DataAccessException {
		Collection<Mechanic> mechanics = this.mechanicService.findAllMechanic();
		assertThat(mechanics.size()).isEqualTo(2);
	}
	
	
	@Test
	@Transactional
	void shouldInsertdNewMechanic() throws DataAccessException, DuplicatedPersonDni {
				
		//mechanic.setDni(null);
		this.mechanicService.saveMechanic(mechanic);
		Collection<Mechanic> mechanics = this.mechanicService.findAllMechanic();
		assertThat(mechanics.size()).isEqualTo(3);
		
	}
	
	@Test
	@DisplayName("Save Mechanic with empty dni")
	@Transactional
	void shouldNotInsertdNewMechanicDNI() throws DataAccessException {
				
		
		mechanic.setDni("");
		mechanic.setType(Type.ENGINE);
		Collection<Mechanic> mechanics = this.mechanicService.findAllMechanic();
		assertThat(mechanics.size()).isEqualTo(2);
		assertThrows(ConstraintViolationException.class,() ->{mechanicService.saveMechanic(mechanic);
		em.flush();});
		
	}
	
	@Test
	@DisplayName("Save Mechanic with wrong dni")
	@Transactional
	void shouldNotInsertdNewMechanicWrongDNI() throws DataAccessException {
				
		
		mechanic.setDni("gadhg1718");
		mechanic.setType(Type.ENGINE);
		Collection<Mechanic> mechanics = this.mechanicService.findAllMechanic();
		assertThat(mechanics.size()).isEqualTo(2);
		assertThrows(ConstraintViolationException.class,() ->{mechanicService.saveMechanic(mechanic);
		em.flush();});
		
	}
	
	@Test
	@DisplayName("Save Mechanic with null type")
	@Transactional
	void shouldNotInsertdNewMechanicWithoutType() throws DataAccessException {
				
		
		//mechanic.setDni("12345678T");
		mechanic.setType(null);
		Collection<Mechanic> mechanics = this.mechanicService.findAllMechanic();
		assertThat(mechanics.size()).isEqualTo(2);
		assertThrows(ConstraintViolationException.class,() ->{mechanicService.saveMechanic(mechanic);
		em.flush();});
		
	}
	
	@Test
	@DisplayName("Save Team with all null")
	@Transactional
	void shouldNotInsertdNewMechanicAllNull() throws DataAccessException {
				
		
		mechanic.setDni(null);
		mechanic.setType(null);
		mechanic.setBirthDate(null);
		mechanic.setFirstName(null);
		mechanic.setFirstName(null);
		mechanic.setNationality(null);
		mechanic.setResidence(null);
		mechanic.setUser(null);
		Collection<Mechanic> mechanics = this.mechanicService.findAllMechanic();
		assertThat(mechanics.size()).isEqualTo(2);
		assertThrows(ConstraintViolationException.class,() ->{mechanicService.saveMechanic(mechanic);
		em.flush();});
		
	}
	
	@Test
	@DisplayName("Save Team with all empty")
	@Transactional
	void shouldNotInsertdNewMechanicAllEmpty() throws DataAccessException {
				
		
		mechanic.setDni("");
		//mechanic.setType("");
		//mechanic.setBirthDate("");
		mechanic.setFirstName("");
		mechanic.setFirstName("");
		mechanic.setNationality("");
		mechanic.setResidence("");
		//mechanic.setUser(null);
		Collection<Mechanic> mechanics = this.mechanicService.findAllMechanic();
		assertThat(mechanics.size()).isEqualTo(2);
		assertThrows(ConstraintViolationException.class,() ->{mechanicService.saveMechanic(mechanic);
		em.flush();});
		
	}
	
	@Test
	@DisplayName("Edit mechanic with already used dni")
	@Transactional
	void shouldThrowExceptionEditingMechanicWithAlreadyUsedDni() throws DataAccessException, DuplicatedTeamNIF {

		mechanic.setDni("12345678E");

		//this.teamService.saveTeam(team2);

		

		assertThrows(DataIntegrityViolationException.class, () -> {
			this.mechanicService.saveMechanic(mechanic);
			em.flush();
		});
	}
	

}
