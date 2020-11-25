package org.springframework.samples.petclinic.service;

import java.util.Collection;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Motorcycle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MotorcycleServiceTest {
	
	@Autowired
	protected MotorcycleService motorcycleService;
	
	private Motorcycle motorcycle;
	
	
	
	@BeforeEach
	void setup() {
		
		motorcycle = this.motorcycleService.findMotorcycleById(1);
		
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
	

}
