package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PilotValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Pilot.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Pilot pilot = (Pilot) target;
		Double height = pilot.getHeight();
		Double weight = pilot.getWeight();
		LocalDate birthDate = pilot.getBirthDate();
		
		//Weight validator
		if(weight<0) {
			errors.rejectValue("Weight", "Un piloto no puede tener peso negativo", "Un piloto no puede tener peso negativo");
		}
		//Height validator
		if(height<0) {
			errors.rejectValue("Height", "Un piloto no puede tener altura negativa", "Un piloto no puede tener altura negativa");
		}
		//Birthdate validator
		if(LocalDate.now().getYear() - birthDate.getYear()<18) {
			errors.rejectValue("BirthDate", "Un piloto debe ser mayor de edad", "Un piloto debe ser mayor de edad");
		}
		
	}

}
