package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import java.util.Date;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PilotValidator implements Validator {

	private static final String REQUIRED = "required";
	private static final String RANGED = "Is not in range";
	private static final String AFTER = "has to be a least 18 years old";

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

		if (pilot.getNumber() == null) {
			errors.rejectValue("number", REQUIRED, REQUIRED);
		}

		if (pilot.getDni() == null || pilot.getDni() == "") {
			errors.rejectValue("dni", REQUIRED, REQUIRED);
		}

		if (pilot.getFirstName() == null || pilot.getFirstName() == "") {
			errors.rejectValue("firstName", REQUIRED, REQUIRED);
		}

		if (pilot.getLastName() == null || pilot.getLastName() == "") {
			errors.rejectValue("lastName", REQUIRED, REQUIRED);
		}

		if (pilot.getResidence() == null || pilot.getResidence() == "") {
			errors.rejectValue("residence", REQUIRED, REQUIRED);
		}

		if (pilot.getNationality() == null || pilot.getNationality() == "") {
			errors.rejectValue("nationality", REQUIRED, REQUIRED);
		}

		if (pilot.getWeight() == null) {
			errors.rejectValue("weight", REQUIRED, REQUIRED);
		}

		if (pilot.getHeight() == null) {
			errors.rejectValue("height", REQUIRED, REQUIRED);
		}

		if (pilot.getUser().getUsername() == null || pilot.getUser().getUsername() == "") {
			errors.rejectValue("user.username", REQUIRED, REQUIRED);
		}

		if (pilot.getUser().getPassword() == null || pilot.getUser().getPassword() == "") {
			errors.rejectValue("user.password", REQUIRED, REQUIRED);
		}

		if (birthDate == null) {
			errors.rejectValue("birthDate", REQUIRED, REQUIRED);
		}

		if (weight != null) {
			// Weight validator
			if (weight < 0 || weight > 100) {
				errors.rejectValue("weight", RANGED, RANGED);
			}
		}
		if (height != null) {
			// Height validator
			if (height < 0 || height > 2) {
				errors.rejectValue("height", RANGED, RANGED);
			}
		}

		if (pilot.getDni() != null || pilot.getDni() != "") {
			if (!pilot.getDni().matches("\\d{8}[A-HJ-NP-TV-Z]")) {
				errors.rejectValue("dni", "have to match", "have to maatch");
			}
		}

		if (pilot.getNumber() != null) {
			if (pilot.getNumber() < 0 || pilot.getNumber() > 99) {
				errors.rejectValue("number", RANGED, RANGED);
			}
		}
		// Birthdate validator

		if (birthDate != null) {
			if (LocalDate.now().getYear() - birthDate.getYear() < 18) {
				errors.rejectValue("birthDate", AFTER, AFTER);
			}
		}

	}

}
