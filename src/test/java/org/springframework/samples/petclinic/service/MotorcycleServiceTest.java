package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Motorcycle;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MotorcycleServiceTest {

	@Autowired
	protected MotorcycleService motorcycleService;

	@Autowired
	protected PilotService pilotService;

	private Motorcycle motorcycle;
	private Pilot piloto;

	@BeforeEach
	void setup() {

		// Se obtiene el piloto con Id = 3
		piloto = pilotService.findById(3);
    motorcycle = this.motorcycleService.findMotorcycleById(1);
		

	}

	@Test
	@Transactional
	@DisplayName("Inserting new motorcycle to pilot")
	public void shouldInsertNewMotorcycle() throws DataAccessException {

		// Se crea una moto nueva y se le asocia al piloto anterior

		motorcycle = new Motorcycle();
		motorcycle.setId(3);
		motorcycle.setBrand("Kawasaki");
		motorcycle.setDisplacement(1999);
		motorcycle.setHorsePower(350);
		motorcycle.setMaxSpeed(370.5);
		motorcycle.setWeight(140);
		motorcycle.setPilot(piloto);
		motorcycle.setTankCapacity(20.5);

		// Y se llama al metodo del servicio para guardar la moto
		this.motorcycleService.saveMoto(motorcycle);
		Integer bike = this.motorcycleService.countBikes(piloto.getId());

		// Finalmente, si el numero de motos de un piloto es 1 significa que se
		// ha guardado correctamente.
		assertThat(bike.equals(1));
	}

	@Test
	@Transactional
	@DisplayName("Find motorcycle with pilotId")
	public void shouldFindMotorcycleById() throws DataAccessException {
		Motorcycle bike = this.motorcycleService.findMotorcycleById(2);
		assertThat(bike.getBrand().equals("HONDA"));
	}
  
  
  	@Test
	@Transactional
	void shouldUpdateFieldMotorcycle() throws DataAccessException{
		
		String brand = "VESPA";
		motorcycle.setBrand(brand);
		this.motorcycleService.saveMoto(motorcycle);
		Collection<Motorcycle> motorcycles = this.motorcycleService.findAll();
		assertThat(motorcycles.size()).isEqualTo(2);
		assertThat(motorcycle.getBrand()).isEqualTo(brand);
	}
  	
  	@Test
	@Transactional
	void shouldNotUpdateFieldMotorcycle() throws DataAccessException{
		
		String brand = "";
		motorcycle.setBrand(brand);
		//this.motorcycleService.saveMoto(motorcycle);
		Collection<Motorcycle> motorcycles = this.motorcycleService.findAll();
		//assertThat(motorcycles.size()).isEqualTo(1);
		assertThrows(ConstraintViolationException.class,
					() ->{motorcycleService.saveMoto(motorcycle);});

	}
  	
  	

}

