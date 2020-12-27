package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.springframework.samples.petclinic.model.Motorcycle;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MotorcycleValidate implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Motorcycle.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Motorcycle moto = (Motorcycle) target;
		String brand = moto.getBrand();
		Double maxSpeed = moto.getMaxSpeed();
		Integer displacemant = moto.getDisplacement();
		Integer horsePower = moto.getHorsePower();
		Pilot piloto = moto.getPilot();
		Double capacity = moto.getTankCapacity();
		Integer weight = moto.getWeight();

		// Brand validator
		if (brand == " " || brand == null) {
			errors.rejectValue("brand", "Una moto no puede tener el nombre vacio o nulo",
					"Una moto no puede tener el nombre vacio o nulo");
		}
		// Max Speed validator
		if (maxSpeed == null || maxSpeed < 0) {
			errors.rejectValue("maxSpeed", "Una moto no puede tener la velocidad maxima negativa",
					"Una moto no puede tener la velocidad maxima negativa");
		}
		// Displacement validator
		if (displacemant == null || displacemant < 0) {
			errors.rejectValue("displacement", "Una moto no puede tener la distancia negativa",
					"Una moto no puede tener la distancia negativa");
		}
		// Horse Power validator
		if (horsePower == null || horsePower < 0) {
			errors.rejectValue("horsePower", "Una moto no puede tener los caballos de potencia negativa",
					"Una moto no puede tener los caballos de potencia negativa");
		}
		
		// Capacity validator
		if (capacity == null || capacity < 18) {
			errors.rejectValue("tankCapacity", "Una moto no puede tener la capacidad negativa", "Una moto no puede tener la capacidad negativa");
		}

		// Weight validator
		if (weight==null || weight < 0) {
			errors.rejectValue("weight", "Una moto no puede tener peso negativo",
					"Una moto no puede tener peso negativo");
		}
	}

}
