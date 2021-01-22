package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MechanicValidator implements Validator {

	private static final String REQUIRED = "required";
	private static final String AFTER = "has to be a least 18 years old";

	@Override
	public void validate(Object obj, Errors errors) {
		Mechanic mechanic = (Mechanic) obj;

		if (mechanic.getDni() == null || mechanic.getDni() == "") {
			errors.rejectValue("dni", REQUIRED, REQUIRED);
		}

		if (mechanic.getDni() != null) {
			if (!mechanic.getDni().matches("\\d{8}[A-HJ-NP-TV-Z]")) {
				errors.rejectValue("dni", "have to match", "have to maatch");
			}
		}

		if (mechanic.getFirstName() == null || mechanic.getFirstName() == "") {
			errors.rejectValue("firstName", REQUIRED, REQUIRED);
		}

		if (mechanic.getLastName() == null || mechanic.getLastName() == "") {
			errors.rejectValue("lastName", REQUIRED, REQUIRED);
		}

		if (mechanic.getResidence() == null || mechanic.getResidence() == "") {
			errors.rejectValue("residence", REQUIRED, REQUIRED);
		}

		if (mechanic.getNationality() == null || mechanic.getNationality() == "") {
			errors.rejectValue("nationality", REQUIRED, REQUIRED);
		}

		if (mechanic.getUser().getUsername() == null || mechanic.getUser().getUsername() == "") {
			errors.rejectValue("user.username", REQUIRED, REQUIRED);
		}

		if (mechanic.getUser().getPassword() == null || mechanic.getUser().getPassword() == "") {
			errors.rejectValue("user.password", REQUIRED, REQUIRED);
		}

		if (mechanic.getType() == null || mechanic.getType().equals("")) {
			errors.rejectValue("type", REQUIRED, REQUIRED);
		}

		LocalDate date = LocalDate.now();

		if (mechanic.getBirthDate() == null) {
			errors.rejectValue("birthDate", REQUIRED, REQUIRED);
		}

		if (mechanic.getBirthDate() != null) {
			if (mechanic.getBirthDate() != null) {
				System.out.println(ChronoUnit.DAYS.between(mechanic.getBirthDate(), date));
				if (ChronoUnit.DAYS.between(mechanic.getBirthDate(), date) < 18 * 365) {
					errors.rejectValue("birthDate", AFTER, AFTER);
				}
			}
		}

	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Mechanic.class.isAssignableFrom(clazz);
	}

}