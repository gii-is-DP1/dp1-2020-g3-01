package org.springframework.samples.petclinic.web;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class GrandPrixValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return GrandPrix.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		GrandPrix gp = (GrandPrix) target;

		String circuit = gp.getCircuit();
		String location = gp.getLocation();
		Integer laps = gp.getLaps();
		Double distance = gp.getDistance();
		Date dayOfRace = gp.getDayOfRace();

		if (dayOfRace == null || dayOfRace.before(Date.from(Instant.now()))) {
			errors.rejectValue("dayOfRace", "La fecha de carrera no puede estar vacía o ser anterior a la fecha actual",
					"La fecha de carrera no puede estar vacía o ser anterior a la fecha actual");
		}
		
		if (circuit == null || circuit.length() <= 3 || circuit.length() >= 50) {
			errors.rejectValue("circuit", "El circuito no puede ser nulo y tiene que tener entre 3 y 50 caracteres",
					"El circuito no puede ser nulo y tiene que tener entre 3 y 50 caracteres");
		}
		
		if (location == null || location.length() <= 3 || location.length() >= 50) {
			errors.rejectValue("location",
					"La localización no puede ser nula y tiene que tener entre 3 y 50 caracteres",
					"La localización no puede ser nula y tiene que tener entre 3 y 50 caracteres");
		}
		
		if (laps == null || laps < 0 || laps >= 30) {
			errors.rejectValue("laps", "El nº de vueltas no puede ser negativo o mayor que 30",
					"El nº de vueltas no puede ser negativo o mayor que 30");
		}
		
		if (distance == null || distance < 0 || laps >= 200) {
			errors.rejectValue("distance", "La distancia del circuito no puede ser negativa o mayor que 200km",
					"La distancia del circuito no puede ser negativa o mayor que 200km");
		}

	}

}
