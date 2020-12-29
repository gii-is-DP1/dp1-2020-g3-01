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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.petclinic.service.exceptions.TwoMaxPilotPerTeamException;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PilotServiceTest {
	@Autowired
	protected PilotService pilotService;
	
	private Pilot pilot;
	private Team team;
	
	@Autowired
	EntityManager em;
	
	@BeforeEach
	void setUp(){
		
		pilot = new Pilot();
		pilot.setFirstName("Alejandro");
		pilot.setLastName("Sevillano");
		pilot.setDni("87654321M");
		pilot.setHeight(1.75);
		pilot.setWeight(80.0);
		LocalDate fechaNacimiento = LocalDate.of(1999, 12, 29);
		pilot.setBirthDate(fechaNacimiento);
		pilot.setNationality("Spain");
		pilot.setResidence("Spain");
		pilot.setNumber(7);
		User user = new User();
		user.setUsername("alexuxcrack7");
		user.setPassword("4l3xuscr4ck7");
		user.setEnabled(true);
		pilot.setUser(user);
		
	}
	//Casos Positivos
	
	@Test
	@DisplayName("Create a pilot correctly")
	@Transactional
	void shouldInsertNewPilot() throws DataAccessException, TwoMaxPilotPerTeamException{
		
		System.out.println(pilot);
		this.pilotService.savePilot(pilot, team);
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(4);
	}
	
	@Test
	@DisplayName("Modify a Pilot correctly")
	@Transactional
	void shouldModifyPilot() throws DataAccessException, TwoMaxPilotPerTeamException{
		String nuevoNombre = "Alex";
		pilot.setFirstName(nuevoNombre);
		this.pilotService.savePilot(pilot, team);
		assertThat(pilot.getFirstName()).isEqualTo(nuevoNombre);
	}
	
	@Test
	@DisplayName("Delete a Pilot correctly")
	@Transactional
	void shouldDeletePilot() throws DataAccessException, TwoMaxPilotPerTeamException{
		this.pilotService.savePilot(pilot, team);
		Integer id = this.pilot.getId();
		this.pilotService.removePilot(id);
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(3);
	}
	
	@Test
	@DisplayName("Should find the pilot by id")
	@Transactional
	void shouldFindPilotById() throws DataAccessException, TwoMaxPilotPerTeamException{
		this.pilotService.savePilot(pilot, team);
		Integer id = pilot.getId();
		Pilot piloto = this.pilotService.findById(id);
		
		assertThat(piloto.getFirstName()).isEqualTo(pilot.getFirstName());
		assertThat(piloto.getLastName()).isEqualTo(pilot.getLastName());
		assertThat(piloto.getBirthDate()).isEqualTo(pilot.getBirthDate());
		assertThat(piloto.getDni()).isEqualTo(pilot.getDni());
		assertThat(piloto.getHeight()).isEqualTo(pilot.getHeight());
		assertThat(piloto.getWeight()).isEqualTo(pilot.getWeight());
		assertThat(piloto.getNationality()).isEqualTo(pilot.getNationality());
		assertThat(piloto.getResidence()).isEqualTo(pilot.getResidence());
		assertThat(piloto.getNumber()).isEqualTo(pilot.getNumber());
		assertThat(piloto.getUser()).isEqualTo(pilot.getUser());
	}
	
	
//	//Casos Negativos
	
	@Test
	@DisplayName("Save Pilot with empty FirstName")
	@Transactional
	void ShouldNotInsertPilotFirstName() throws DataAccessException {
		pilot.setFirstName("");
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(3);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot, team); em.flush();});
	}
	
	@Test
	@DisplayName("Save Pilot with empty LastName")
	@Transactional
	void ShouldNotInsertPilotLastName() throws DataAccessException {
		pilot.setLastName("");
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(3);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot, team); em.flush();});
	}
	
	@Test
	@DisplayName("Save Pilot with empty DNI")
	@Transactional
	void ShouldNotInsertPilotDNI() throws DataAccessException {
		pilot.setDni("");
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(3);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot, team); em.flush();});
	}
	
	@Test
	@DisplayName("Save Pilot with wrong DNI")
	@Transactional
	void ShouldNotInsertPilotWrongDNI() throws DataAccessException {
		pilot.setDni("abcde678");
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(3);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot, team); em.flush();});
	}
	
	@Test
	@DisplayName("Save Pilot with null Weight")
	@Transactional
	void ShouldNotInsertPilotWeight() throws DataAccessException {
		pilot.setWeight(null);
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(3);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot, team); em.flush();});		
	}
	
	@Test
	@DisplayName("Save Pilot with wrong Weight")
	@Transactional
	void ShouldNotInsertPilotWrongWeight() throws DataAccessException {
		pilot.setWeight(-3.2);
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(3);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot, team); em.flush();});		
	}
	
	@Test
	@DisplayName("Save Pilot with null Height")
	@Transactional
	void ShouldNotInsertPilotHeight() throws DataAccessException {
		pilot.setHeight(null);
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(3);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot, team); em.flush();});
	}
	
	@Test
	@DisplayName("Save Pilot with wrong Height")
	@Transactional
	void ShouldNotInsertPilotWrongHeight() throws DataAccessException {
		pilot.setHeight(-30.);
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(3);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot, team); em.flush();});
	}
	
	@Test
	@DisplayName("Save Pilot with null BirthDate")
	@Transactional
	void ShouldNotInsertNPilotBirthDate() throws DataAccessException {
		pilot.setBirthDate(null);
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(3);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot, team); em.flush();});
	}
	
	@Test
	@DisplayName("Save pilot with empty Residence")
	@Transactional
	void ShouldNotInsertPilotResidence() throws DataAccessException {
		pilot.setResidence("");
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(3);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot, team); em.flush();});
	}
	
	@Test
	@DisplayName("Save pilot with emtpy Nationality")
	@Transactional
	void ShouldNotInsertPilotNationality() throws DataAccessException {
		pilot.setNationality("");
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(3);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot, team); em.flush();});
	}
	
	@Test
	@DisplayName("Save pilot with null Number")
	@Transactional
	void ShouldNotInsertPilotNumber() throws DataAccessException {
		pilot.setNumber(null);
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(3);
		assertThrows(ConstraintViolationException.class, () -> {pilotService.savePilot(pilot, team); em.flush();});
	}
	
	@Test
	@DisplayName("Save pilot with an already used Number")
	@Transactional
	void ShouldNotInsertPilotUsedNumber() throws DataAccessException {
		Pilot piloto = this.pilotService.findById(1);
		pilot.setNumber(piloto.getNumber());
		Collection<Pilot> pilots = this.pilotService.findAllPilots();
		assertThat(pilots.size()).isEqualTo(3);
		assertThrows(DataIntegrityViolationException.class, () -> {pilotService.savePilot(pilot, team); em.flush();});
	}
}
