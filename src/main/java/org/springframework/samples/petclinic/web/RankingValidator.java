package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.Position;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RankingValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return GrandPrix.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		GrandPrix grandPrix = (GrandPrix) target;
		Set<Position> positions = grandPrix.getPositions();

		Set<Integer> posSet = new HashSet<>();
		List<Integer> posList = new ArrayList<Integer>();

		for (Position p : positions) {

			posSet.add(p.getPos());
			posList.add(p.getPos());

		}

		if (posSet.size() != posList.size()) {

			for (Position p : positions) {
				errors.rejectValue(String.valueOf(p.getPilot().getId()), "Un piloto no puede tener peso negativo",
						"Un piloto no puede tener peso negativo");
			}
		}

	}

}
