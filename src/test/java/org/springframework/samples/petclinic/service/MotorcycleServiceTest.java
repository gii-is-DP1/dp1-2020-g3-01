package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import org.springframework.samples.petclinic.model.GrandPrix;
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

	@Autowired
	EntityManager em;

	@BeforeEach
	void setup() {

		// Se obtiene el piloto con Id = 3
		piloto = pilotService.findById(2);

		motorcycle = this.motorcycleService.findMotorcycleById(1);

	}

	// CASOS POSITIVOS

	// Insertar moto correctamente

	@Test
	@Transactional
	@DisplayName("Inserting new motorcycle to pilot")
	public void shouldInsertNewMotorcycle() throws DataAccessException {

		// Se crea una moto nueva y se le asocia al piloto anterior

		Motorcycle motorcycle = new Motorcycle();
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
	@DisplayName("Should find Motorcycle")
	void shouldMotorcycle() {
		Motorcycle motorcycle = this.motorcycleService.findMotorcycleById(1);
		assertThat(motorcycle.getBrand()).isEqualTo("YAMAHA");
	}
	
	@Test
	@Transactional
	@DisplayName("Should find Motorcycle By Pilot")
	void shouldMotorcycleByPilot() {
		Motorcycle motorcycle = this.motorcycleService.findMotorcycleByPilotId(1);
		assertThat(motorcycle.getId()).isEqualTo(1);
	}
	
	@Test
	@Transactional
	@DisplayName("List of all motorcycles")
	void shouldFindAllMotorcycle() {
		Collection<Motorcycle>motorcycles = this.motorcycleService.findAll();
		assertThat(motorcycles.size()).isEqualTo(2);
	}
	
	@Test
	@Transactional
	@DisplayName("Count bikes of pilot")
	void shouldCountMotorcycle() {
		Integer motorcycle = this.motorcycleService.countBikes(1);
		assertThat(motorcycle).isEqualTo(1);
	}

	// Encontrar una moto por el ID de su piloto

	@Test
	@Transactional
	@DisplayName("Find motorcycle with pilotId")
	public void shouldFindMotorcycleById() throws DataAccessException {
		Motorcycle bike = this.motorcycleService.findMotorcycleById(2);
		assertThat(bike.getBrand().equals("HONDA"));
	}

	// Editar correctamente una moto

	@Test
	@Transactional
	void shouldUpdateFieldMotorcycle() throws DataAccessException {

		String brand = "VESPA";
		motorcycle.setBrand(brand);
		this.motorcycleService.saveMoto(motorcycle);
		Collection<Motorcycle> motorcycles = this.motorcycleService.findAll();
		assertThat(motorcycles.size()).isEqualTo(2);
		assertThat(motorcycle.getBrand()).isEqualTo(brand);
	}



	// Eliminar moto
	
	@Test
	@DisplayName("Delete Motorcycle")
	@Transactional
	void shouldDeleteMotorcycle() throws DataAccessException {

		this.motorcycleService.removeBike(motorcycle.getId());
		Collection<Motorcycle> motorcycles = this.motorcycleService.findAll();
		assertThat(motorcycles.size()).isEqualTo(1);

	}

	// CASOS NEGATIVOS
	
		// Crear moto con marca corta de caracteres
		
		@Test
		@DisplayName("Create Motorcycle with short brand")
		@Transactional
		void shouldNotCreatedMotorcycleShortBrand() throws DataAccessException {
			
			Motorcycle motorcycle = new Motorcycle();
			motorcycle.setId(4);
			motorcycle.setBrand("fe");
			motorcycle.setDisplacement(1500);
			motorcycle.setHorsePower(350);
			motorcycle.setMaxSpeed(370.5);
			motorcycle.setWeight(140);
			motorcycle.setPilot(piloto);
			motorcycle.setTankCapacity(20.5);

			assertThrows(ConstraintViolationException.class, () -> {
				motorcycleService.saveMoto(motorcycle);
				em.flush();
			});
		}	
		
		// Crear moto con poca cilindrada
		
		@Test
		@DisplayName("Create Motorcycle with short displacement")
		@Transactional
		void shouldNotCreatedMotorcycleShortDisplacement() throws DataAccessException {
			
			Motorcycle motorcycle = new Motorcycle();
			motorcycle.setId(4);
			motorcycle.setBrand("Yamaha");
			motorcycle.setDisplacement(50);
			motorcycle.setHorsePower(350);
			motorcycle.setMaxSpeed(370.5);
			motorcycle.setWeight(140);
			motorcycle.setPilot(piloto);
			motorcycle.setTankCapacity(20.5);

			assertThrows(ConstraintViolationException.class, () -> {
					motorcycleService.saveMoto(motorcycle);
					em.flush();
				});
		}	
		
		// Crear moto con mucha cilindrada
		
		@Test
		@DisplayName("Create Motorcycle with long displacement")
		@Transactional
		void shouldNotCreatedMotorcycleLongDisplacement() throws DataAccessException {
			
			Motorcycle motorcycle = new Motorcycle();
			motorcycle.setId(4);
			motorcycle.setBrand("Yamaha");
			motorcycle.setDisplacement(8050);
			motorcycle.setHorsePower(350);
			motorcycle.setMaxSpeed(370.5);
			motorcycle.setWeight(140);
			motorcycle.setPilot(piloto);
			motorcycle.setTankCapacity(20.5);

			assertThrows(ConstraintViolationException.class, () -> {
					motorcycleService.saveMoto(motorcycle);
					em.flush();
			});
		}
		
		// Crear moto con poca potencia
		
		@Test
		@DisplayName("Create Motorcycle with short horsepower")
		@Transactional
		void shouldNotCreatedMotorcycleShortHorsepower() throws DataAccessException {
					
			Motorcycle motorcycle = new Motorcycle();
			motorcycle.setId(4);
			motorcycle.setBrand("Yamaha");
			motorcycle.setDisplacement(1050);
			motorcycle.setHorsePower(30);
			motorcycle.setMaxSpeed(370.5);
			motorcycle.setWeight(140);
			motorcycle.setPilot(piloto);
			motorcycle.setTankCapacity(20.5);

			assertThrows(ConstraintViolationException.class, () -> {
					motorcycleService.saveMoto(motorcycle);
					em.flush();
			});
		}		
		
		// Crear moto con mucha potencia
		
		@Test
		@DisplayName("Create Motorcycle with large horsepower")
		@Transactional
		void shouldNotCreatedMotorcycleLargeHorsepower() throws DataAccessException {
					
			Motorcycle motorcycle = new Motorcycle();
			motorcycle.setId(4);
			motorcycle.setBrand("Yamaha");
			motorcycle.setDisplacement(1500);
			motorcycle.setHorsePower(450);
			motorcycle.setMaxSpeed(370.5);
			motorcycle.setWeight(140);
			motorcycle.setPilot(piloto);
			motorcycle.setTankCapacity(20.5);

			assertThrows(ConstraintViolationException.class, () -> {
				motorcycleService.saveMoto(motorcycle);
				em.flush();
			});
		}
		
		// Crear moto con peso negativo
		
		@Test
		@DisplayName("Create Motorcycle with negative weight")
		@Transactional
		void shouldNotCreatedMotorcycleNegativeWeight() throws DataAccessException {
					
			Motorcycle motorcycle = new Motorcycle();
			motorcycle.setId(4);
			motorcycle.setBrand("Yamaha");
			motorcycle.setDisplacement(1500);
			motorcycle.setHorsePower(350);
			motorcycle.setMaxSpeed(370.5);
			motorcycle.setWeight(-140);
			motorcycle.setPilot(piloto);
			motorcycle.setTankCapacity(20.5);

			assertThrows(ConstraintViolationException.class, () -> {
				motorcycleService.saveMoto(motorcycle);
				em.flush();
			});
		}
		
		// Crear moto con mucho peso
		
		@Test
		@DisplayName("Create Motorcycle with large weight")
		@Transactional
		void shouldNotCreatedMotorcycleLargeWeight() throws DataAccessException {
						
			Motorcycle motorcycle = new Motorcycle();
			motorcycle.setId(4);
			motorcycle.setBrand("Yamaha");
			motorcycle.setDisplacement(1500);
			motorcycle.setHorsePower(350);
			motorcycle.setMaxSpeed(370.5);
			motorcycle.setWeight(540);
			motorcycle.setPilot(piloto);
			motorcycle.setTankCapacity(20.5);

			assertThrows(ConstraintViolationException.class, () -> {
				motorcycleService.saveMoto(motorcycle);
				em.flush();
			});
		}
		
		// Crear moto con tanque de gasolina negativo
		
		@Test
		@DisplayName("Create Motorcycle with negative capacity")
		@Transactional
		void shouldNotCreatedMotorcycleNegativeCapacity() throws DataAccessException {
								
			Motorcycle motorcycle = new Motorcycle();
			motorcycle.setId(4);
			motorcycle.setBrand("Yamaha");
			motorcycle.setDisplacement(1500);
			motorcycle.setHorsePower(350);
			motorcycle.setMaxSpeed(370.5);
			motorcycle.setWeight(540);
			motorcycle.setPilot(piloto);
			motorcycle.setTankCapacity(-20.5);

			assertThrows(ConstraintViolationException.class, () -> {
				motorcycleService.saveMoto(motorcycle);
				em.flush();
			});
		}
		
		// Crear moto con tanque de gasolina negativo
		
		@Test
		@DisplayName("Create Motorcycle with large capacity")
		@Transactional
		void shouldNotCreatedMotorcycleLargeCapacity() throws DataAccessException {
									
			Motorcycle motorcycle = new Motorcycle();
			motorcycle.setId(4);
			motorcycle.setBrand("Yamaha");
			motorcycle.setDisplacement(1500);
			motorcycle.setHorsePower(350);
			motorcycle.setMaxSpeed(370.5);
			motorcycle.setWeight(140);
			motorcycle.setPilot(piloto);
			motorcycle.setTankCapacity(40.5);

			assertThrows(ConstraintViolationException.class, () -> {
				motorcycleService.saveMoto(motorcycle);
				em.flush();
			});
		}
		
		// Crear moto con velocidad maxima negativa
		
		@Test
		@DisplayName("Create Motorcycle with negative max speed")
		@Transactional
		void shouldNotCreatedMotorcycleNegativeMaxSpeed() throws DataAccessException {
											
			Motorcycle motorcycle = new Motorcycle();
			motorcycle.setId(4);
			motorcycle.setBrand("Yamaha");
			motorcycle.setDisplacement(1500);
			motorcycle.setHorsePower(350);
			motorcycle.setMaxSpeed(-370.5);
			motorcycle.setWeight(140);
			motorcycle.setPilot(piloto);
			motorcycle.setTankCapacity(20.5);

			assertThrows(ConstraintViolationException.class, () -> {
				motorcycleService.saveMoto(motorcycle);
				em.flush();
			});
		}
				
		// Crear moto con tanque de gasolina negativo
				
		@Test
		@DisplayName("Create Motorcycle with large max speed")
		@Transactional
		void shouldNotCreatedMotorcycleLargeMaxSpeed() throws DataAccessException {
											
			Motorcycle motorcycle = new Motorcycle();
			motorcycle.setId(4);
			motorcycle.setBrand("Yamaha");
			motorcycle.setDisplacement(1500);
			motorcycle.setHorsePower(350);
			motorcycle.setMaxSpeed(670.5);
			motorcycle.setWeight(140);
			motorcycle.setPilot(piloto);
			motorcycle.setTankCapacity(20.5);

			assertThrows(ConstraintViolationException.class, () -> {
				motorcycleService.saveMoto(motorcycle);
				em.flush();
			});
		}
		
		// Editar moto con demasiada cilindrada
		
		@Test
		@DisplayName("Update Motorcycle with long displacement")
		@Transactional
		void shouldNotUpdateFieldMotorcycleDisplacementLong() throws DataAccessException {
			Integer displacement = 6878132;
			motorcycle.setDisplacement(displacement);

			assertThrows(ConstraintViolationException.class, () -> {
				motorcycleService.saveMoto(motorcycle);
					em.flush();
			});
		}	
	
	
	// Editar moto con valores incorrectos
	
	@Test
	@DisplayName("Edit moto incorrectly")
	@Transactional
	void shouldThrowExceptionEditingMotorcycleIncorrectParameters() throws DataAccessException {

		Motorcycle moto = motorcycleService.findMotorcycleById(1);

		moto.setWeight(-140);

		assertThrows(ConstraintViolationException.class, () -> {
			this.motorcycleService.saveMoto(moto);
			em.flush();
		});
	}
	
	@Test
	@DisplayName("Edit moto incorrectly Range Weight")
	@Transactional
	void shouldThrowExceptionEditingMotorcycleIncorrectWeight() throws DataAccessException {

		Motorcycle moto = motorcycleService.findMotorcycleById(1);

		moto.setWeight(251);

		assertThrows(ConstraintViolationException.class, () -> {
			this.motorcycleService.saveMoto(moto);
			em.flush();
		});
	}
	
	// Editar moto con insuficiente cilindrada
	
	@Test
	@DisplayName("Update Motorcycle with short displacement")
	@Transactional
	void shouldNotUpdateFieldMotorcycleDisplacementShort() throws DataAccessException {
		Integer displacement = -1;
		motorcycle.setDisplacement(displacement);
		
		assertThrows(ConstraintViolationException.class, () -> {
			motorcycleService.saveMoto(motorcycle);
			em.flush();
		});

	}
	
	@Test
	@DisplayName("Edit moto negativ tank")
	@Transactional
	void shouldThrowExceptionEditingMotorcycleIncorrectParametersTank() throws DataAccessException {

		Motorcycle moto = motorcycleService.findMotorcycleById(1);

		moto.setTankCapacity(-1.0);

		assertThrows(ConstraintViolationException.class, () -> {
			this.motorcycleService.saveMoto(moto);
			em.flush();
		});
	}
	
	@Test
	@DisplayName("Edit moto range tank")
	@Transactional
	void shouldThrowExceptionEditingMotorcycleIncorrectParametersRangeTank() throws DataAccessException {

		Motorcycle moto = motorcycleService.findMotorcycleById(1);

		moto.setTankCapacity(23.0);

		assertThrows(ConstraintViolationException.class, () -> {
			this.motorcycleService.saveMoto(moto);
			em.flush();
		});
	}
	
	@Test
	@DisplayName("Edit moto negativ speed")
	@Transactional
	void shouldThrowExceptionEditingMotorcycleIncorrectParametersSpeed() throws DataAccessException {

		Motorcycle moto = motorcycleService.findMotorcycleById(1);

		moto.setMaxSpeed(-1.0);

		assertThrows(ConstraintViolationException.class, () -> {
			this.motorcycleService.saveMoto(moto);
			em.flush();
		});
	}
	
	@Test
	@DisplayName("Edit moto range speed")
	@Transactional
	void shouldThrowExceptionEditingMotorcycleIncorrectParametersRangeSpeed() throws DataAccessException {

		Motorcycle moto = motorcycleService.findMotorcycleById(1);

		moto.setMaxSpeed(500.0);

		assertThrows(ConstraintViolationException.class, () -> {
			this.motorcycleService.saveMoto(moto);
			em.flush();
		});
	}
	
	
	@Test
	@DisplayName("Edit moto negativ horse power")
	@Transactional
	void shouldThrowExceptionEditingMotorcycleIncorrectParametersHorse() throws DataAccessException {

		Motorcycle moto = motorcycleService.findMotorcycleById(1);

		moto.setHorsePower(-1);

		assertThrows(ConstraintViolationException.class, () -> {
			this.motorcycleService.saveMoto(moto);
			em.flush();
		});
	}
	
	@Test
	@DisplayName("Edit moto range horse power")
	@Transactional
	void shouldThrowExceptionEditingMotorcycleIncorrectParametersRangeHorse() throws DataAccessException {

		Motorcycle moto = motorcycleService.findMotorcycleById(1);

		moto.setHorsePower(1000);

		assertThrows(ConstraintViolationException.class, () -> {
			this.motorcycleService.saveMoto(moto);
			em.flush();
		});
	}
	
	// Editar moto con marca vacia
	
	@Test
	@DisplayName("Update Motorcycle with empty brand")
	@Transactional
	void shouldNotUpdateFieldMotorcycle() throws DataAccessException {

		String brand = "";
		motorcycle.setBrand(brand);
		this.motorcycleService.saveMoto(motorcycle);
		
		assertThrows(ConstraintViolationException.class, () -> {
			motorcycleService.saveMoto(motorcycle);
			em.flush();
		});

	}

}
