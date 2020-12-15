package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PilotServiceTest {
	
	@Autowired
	protected PilotService pilotService;
	
	private Pilot pilot;
	
	@Autowired
	EntityManager em;
	
	@BeforeEach
	void setUp(){
		
		pilot = new Pilot();
		pilot.setFirstName("Alejandro");
		pilot.setLastName("Sevillano");
		pilot.setDni("87654321M");
		pilot.setHeight(1.75);
		pilot.setWeight(80.);
		LocalDate fechaNacimiento = LocalDate.of(1999, 12, 29);
		pilot.setBirthDate(fechaNacimiento);
		pilot.setNationality("Spain");
		pilot.setResidence("Spain");
		pilot.setId(3);
		pilot.setNumber(7);
		User user = new User();
		user.setUsername("alexuxcrack7");
		user.setPassword("4l3xuscr4ck7");
		user.setEnabled(true);
		pilot.setUser(user);
		
	}
	
	@Test
	@Transactional
	void shouldInsertNewPilot() throws DataAccessException{
		
		this.pilotService.savePilot(pilot);
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(3);
	}
	
	@Test
	@DisplayName("Save Pilot with empty FirstName")
	@Transactional
	void ShouldNotInsertNewPilotFirstName() throws DataAccessException {
		pilot.setFirstName("");
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(2);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot); em.flush();});
	}
	
	@Test
	@DisplayName("Save Pilot with empty LastName")
	@Transactional
	void ShouldNotInsertNewPilotLastName() throws DataAccessException {
		pilot.setLastName("");
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(2);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot); em.flush();});
	}
	
	@Test
	@DisplayName("Save Pilot with empty DNI")
	@Transactional
	void ShouldNotInsertNewPilotDNI() throws DataAccessException {
		pilot.setDni("");
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(2);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot); em.flush();});
	}
	
	@Test
	@DisplayName("Save Pilot with wrong DNI")
	@Transactional
	void ShouldNotInsertNewPilotWrongDNI() throws DataAccessException {
		pilot.setDni("abcde678");
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(2);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot); em.flush();});
	}
	
	@Test
	@DisplayName("Save Pilot with null Weight")
	@Transactional
	void ShouldNotInsertNewPilotWeight() throws DataAccessException {
		pilot.setWeight(null);
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(2);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot); em.flush();});		
	}
	
	@Test
	@DisplayName("Save Pilot with wrong Weight")
	@Transactional
	void ShouldNotInsertNewPilotWrongWeight() throws DataAccessException {
		pilot.setWeight(-3.2);
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(2);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot); em.flush();});		
	}
	
	@Test
	@DisplayName("Save Pilot with null Height")
	@Transactional
	void ShouldNotInsertNewPilotHeight() throws DataAccessException {
		pilot.setHeight(null);
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(2);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot); em.flush();});
	}
	
	@Test
	@DisplayName("Save Pilot with wrong Height")
	@Transactional
	void ShouldNotInsertNewPilotWrongHeight() throws DataAccessException {
		pilot.setHeight(-30.);
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(2);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot); em.flush();});
	}
	
	@Test
	@DisplayName("Save Pilot with null BirthDate")
	@Transactional
	void ShouldNotInsertNewPilotBirthDate() throws DataAccessException {
		pilot.setBirthDate(null);
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(2);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot); em.flush();});
	}
	
	@Test
	@DisplayName("Save under-age Pilot")
	@Transactional
	void ShouldNotInsertNewPilotUnderAge() throws DataAccessException {
		LocalDate bd = LocalDate.of(2009, 12, 29);
		pilot.setBirthDate(bd);
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(2);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot); em.flush();});
	}

}
