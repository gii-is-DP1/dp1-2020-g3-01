package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
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
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Position;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class RankingServiceTests {

	@Autowired
	GrandPrixService grandPrixService;
	
	@Autowired
	PositionService positionService;
	
	@Autowired
	PilotService pilotService;
	
	@Autowired
	UserService userService;

	private GrandPrix grandPrix;
	
	@Autowired
	EntityManager em;

	@BeforeEach
	void setUp() {
		
		User user = new User();
		user.setUsername("jantontio");
		user.setPassword("parejo");
		user.setEnabled(true);

		User user2 = new User();
		user2.setUsername("breathOf");
		user2.setPassword("theWild");
		user2.setEnabled(true);
		
		Pilot pt = new Pilot();
		pt.setUser(user2);
		LocalDate date = LocalDate.now();
		pt.setBirthDate(date);
		pt.setFirstName("Alejandros");
		pt.setLastName("Dumas");
		pt.setDni("23124568D");
		pt.setNationality("Russian");
		pt.setResidence("Moscu");
		pt.setHeight(1.50);
		pt.setWeight(80.2);
		pt.setNumber(11);
		pt.setId(7);
		
		Pilot pt2 = new Pilot();
		pt2.setUser(user2);
		//LocalDate date = LocalDate.now();
		pt2.setBirthDate(date);
		pt2.setFirstName("Alejandros");
		pt2.setLastName("Dumas");
		pt2.setDni("23124568D");
		pt2.setNationality("Russian");
		pt2.setResidence("Moscu");
		pt2.setHeight(1.50);
		pt2.setWeight(80.2);
		pt2.setNumber(11);
		pt2.setId(7);
		
		Manager m = new Manager();
		m.setUser(user);
		// LocalDate date = LocalDate.now();
		m.setBirthDate(date);
		m.setFirstName("Jose Antontio");
		m.setLastName("Parejo");
		m.setNationality("Spain");
		m.setResidence("Seville");
		m.setId(6);
		m.setDni("89675432L");

		Set<Pilot> setP = new HashSet<>();
		setP.add(pt);
		setP.add(pt2);
		
		Team team1 = new Team();
		team1.setId(3);
		Date fecha = new Date();
		team1.setCreationDate(fecha);
		team1.setName("Ducati");
		team1.setManager(m);
		team1.setNif("12345678X");
		team1.setPilot(setP);

		Set<Team> setT = new HashSet<>();
		setT.add(team1);
		
		Position po = new Position();
		po.setId(20);
		po.setPilot(pt);
		po.setPoint(25);
		po.setPos(1);

		Set<Position> posi = new HashSet<>();
		posi.add(po);
		
		grandPrix = new GrandPrix();
		grandPrix.setCircuit("Circuit");
		grandPrix.setDistance(123.32);
		grandPrix.setLaps(32);
		grandPrix.setLocation("New Sevilla");
		grandPrix.setId(6);
		grandPrix.setDayOfRace(LocalDate.now());
		grandPrix.setPilots(setP);
		grandPrix.setTeam(setT);
		grandPrix.setPositions(posi);
			
		
	}
	
	@Test
	@DisplayName("Show all positions")
	@Transactional
	void shouldShowAllPositions() throws Exception {

		assertThat(this.grandPrixService.findAllPositionsByGrandPrixId(1).size()).isEqualTo(2);
		
	}
	
	@Test
	@Transactional
	@DisplayName("Inserting message correctly")
	void shouldCreateMessageCorrectly() throws DataAccessException {
		
		Pilot pilot = this.pilotService.findById(1);
		
		Position position = new Position();
		
		position.setId(10);
		position.setPoint(20);
		position.setPos(1);
		position.setPilot(pilot);

		this.positionService.savePosition(position);

		assertThat(positionService.findAll().size()).isEqualTo(3);
	}
	
	@Test
	@Transactional
	@DisplayName("Inserting message incorrectly")
	void shouldNotCreateMessageCorrectly() throws DataAccessException {
		
		Pilot pilot = this.pilotService.findById(1);
		
		Position position = new Position();
		
		position.setId(10);
		position.setPoint(20);
		position.setPos(0);
		position.setPilot(pilot);

		

		assertThat(positionService.findAll().size()).isEqualTo(2);
		assertThrows(ConstraintViolationException.class,() ->{positionService.savePosition(position);
		em.flush();});
	}
}
