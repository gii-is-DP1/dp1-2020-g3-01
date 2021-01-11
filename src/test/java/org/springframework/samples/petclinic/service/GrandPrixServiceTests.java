package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
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
import org.springframework.samples.petclinic.service.exceptions.DuplicatedTeamNIF;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedTeamName;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GrandPrixServiceTests {

	@Autowired
	GrandPrixService grandPrixService;

//	private GrandPrix grandPrix;
	@Autowired
	EntityManager em;

	@BeforeEach
	void setUp() {
		GrandPrix gp = this.grandPrixService.findGPById(1);
	}

	// CASOS POSITIVOS

	// Añadir gran premio con valores correctos

	@Test
	@Transactional
	@DisplayName("Create GrandPrix with correct values")
	void shouldCreateGrandPrix() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		GrandPrix grandPrix = new GrandPrix();
		grandPrix.setLocation("Le Mans, Francia");
		grandPrix.setCircuit("Circuito de LeMans");
		grandPrix.setDistance(113.0);
		grandPrix.setLaps(27);
		LocalDate dayRace = LocalDate.of(2021, 01, 29);
		grandPrix.setDayOfRace(dayRace);

		assertThat(this.grandPrixService.findAll().contains(grandPrix));
	}

	// Añadir segundo gran premio con valores correctos

	@Test
	@Transactional
	@DisplayName("Create GrandPrix with correct values 2")
	void shouldCreateGrandPrix2() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		GrandPrix grandPrix2 = new GrandPrix();
		grandPrix2.setLocation("Silverstone, Inglaterra");
		grandPrix2.setCircuit("Circuito de Silverstone");
		grandPrix2.setDistance(118.0);
		grandPrix2.setLaps(20);
		LocalDate dayRace = LocalDate.of(2021, 02, 15);
		grandPrix2.setDayOfRace(dayRace);

		assertThat(this.grandPrixService.findAll().contains(grandPrix2));
	}

	// CASOS NEGATIVOS

	// Añadir gran premio con localización inferior al numero de caracteres exigido

	@Test
	@Transactional
	@DisplayName("Should not Create GP short location")
	void shouldNotCreateGPShortLocation() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		GrandPrix grandPrix = new GrandPrix();
		grandPrix.setLocation("Si");
		grandPrix.setCircuit("Circuito de Silverstone");
		grandPrix.setDistance(113.0);
		grandPrix.setLaps(27);
		LocalDate dayRace = LocalDate.of(2021, 01, 29);
		grandPrix.setDayOfRace(dayRace);

		assertThrows(ConstraintViolationException.class, () -> {
			this.grandPrixService.saveGP(grandPrix);
			em.flush();
		});
	}

	// Añadir gran premio con circuito inferior al numero de caracteres exigido

	@Test
	@Transactional
	@DisplayName("Should not Create GP short circuit")
	void shouldNotCreateGPShortCircuit() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		GrandPrix grandPrix = new GrandPrix();
		grandPrix.setLocation("Silverstone");
		grandPrix.setCircuit("Ci");
		grandPrix.setDistance(113.0);
		grandPrix.setLaps(27);
		LocalDate dayRace = LocalDate.of(2021, 01, 29);
		grandPrix.setDayOfRace(dayRace);

		assertThrows(ConstraintViolationException.class, () -> {
			this.grandPrixService.saveGP(grandPrix);
			em.flush();
		});
	}

	// Añadir gran premio con numero de vueltas negativo

	@Test
	@Transactional
	@DisplayName("Should not Create GP negative laps")
	void shouldNotCreateGPNegativeLaps() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		GrandPrix grandPrix = new GrandPrix();
		grandPrix.setLocation("Silverstone");
		grandPrix.setCircuit("Circuito de Silverstone");
		grandPrix.setDistance(113.0);
		grandPrix.setLaps(-27);
		LocalDate dayRace = LocalDate.of(2021, 01, 29);
		grandPrix.setDayOfRace(dayRace);

		assertThrows(ConstraintViolationException.class, () -> {
			this.grandPrixService.saveGP(grandPrix);
			em.flush();
		});
	}

	// Añadir gran premio con numero de vueltas maximo al permitido

	@Test
	@Transactional
	@DisplayName("Should not Create GP max laps")
	void shouldNotCreateGPMaxLaps() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		GrandPrix grandPrix = new GrandPrix();
		grandPrix.setLocation("Silverstone");
		grandPrix.setCircuit("Circuito de Silverstone");
		grandPrix.setDistance(113.0);
		grandPrix.setLaps(50);
		LocalDate dayRace = LocalDate.of(2021, 01, 29);
		grandPrix.setDayOfRace(dayRace);

		assertThrows(ConstraintViolationException.class, () -> {
			this.grandPrixService.saveGP(grandPrix);
			em.flush();
		});
	}

	// Añadir gran premio con numero de distancia negativa

	@Test
	@Transactional
	@DisplayName("Should not Create GP negative distance")
	void shouldNotCreateGPNegativeDistance() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		GrandPrix grandPrix = new GrandPrix();
		grandPrix.setLocation("Silverstone");
		grandPrix.setCircuit("Circuito de Silverstone");
		grandPrix.setDistance(-113.0);
		grandPrix.setLaps(10);
		LocalDate dayRace = LocalDate.of(2021, 01, 29);
		grandPrix.setDayOfRace(dayRace);

		assertThrows(ConstraintViolationException.class, () -> {
			this.grandPrixService.saveGP(grandPrix);
			em.flush();
		});
	}

	// Añadir gran premio con numero de vueltas maximo al permitido

	@Test
	@Transactional
	@DisplayName("Should not Create GP max distance")
	void shouldNotCreateGPMaxDistance() throws DataAccessException, DuplicatedTeamName, DuplicatedTeamNIF {

		GrandPrix grandPrix = new GrandPrix();
		grandPrix.setLocation("Silverstone");
		grandPrix.setCircuit("Circuito de Silverstone");
		grandPrix.setDistance(513.0);
		grandPrix.setLaps(20);
		LocalDate dayRace = LocalDate.of(2021, 01, 29);
		grandPrix.setDayOfRace(dayRace);

		assertThrows(ConstraintViolationException.class, () -> {
			this.grandPrixService.saveGP(grandPrix);
			em.flush();
		});
	}
}