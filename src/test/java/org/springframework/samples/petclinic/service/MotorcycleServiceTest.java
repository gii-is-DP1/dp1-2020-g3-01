package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Motorcycle;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MotorcycleServiceTest {

	@Autowired
	protected MotorcycleService motorcycleService;

	private Motorcycle motorcycle;
	private Pilot piloto;
	private User user;

	@BeforeEach
	void setup() {
		piloto = new Pilot();
		LocalDate date = LocalDate.of(1966, 1, 1);
		piloto.setBirthDate(date);
		piloto.setDni("56473829A");
		piloto.setFirstName("Diego Armando");
		piloto.setLastName("Maradona");
		piloto.setHeight(1.65);
		piloto.setId(7);
		piloto.setNationality("Argentino");
		piloto.setNumber(10);
		piloto.setResidence("Buenos Aires");
		piloto.setWeight(96.7);
		user.setUsername("mechanic5");
		user.setPassword("m3ch4n1c5");
		user.setEnabled(true);
		piloto.setUser(user);

		motorcycle = new Motorcycle();
		motorcycle.setBrand("Kawasaki");
		motorcycle.setDisplacement(210);
		motorcycle.setHorsePower(78);
		motorcycle.setId(8);
		motorcycle.setMaxSpeed(520.3);
		motorcycle.setPilot(piloto);
		motorcycle.setTankCapacity(100.3);

	}

	@Test
	@Transactional
	public void shouldInsertNewMotorcycle() throws DataAccessException {
		this.motorcycleService.saveMoto(motorcycle);
		Integer bike = this.motorcycleService.countBikes(piloto.getId());
		assertThat(bike.equals(1));
	}

//	@Test
//    @Transactional
//    void shouldInsertdNewMechanic() throws DataAccessException {
//
//        this.mechanicService.saveMechanic(mechanic);
//        Collection<Mechanic> mechanics = this.mechanicService.findAllMechanic();
//        assertThat(mechanics.size()).isEqualTo(3);
//	}

}
