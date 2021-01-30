package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Position;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.GrandPrixService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.PositionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RankingController {

	private final GrandPrixService grandPrixService;
	private final PositionService positionService;
	private final PilotService pilotService;

	@Autowired
	public RankingController(GrandPrixService grandPrixService, PositionService positionService,
			PilotService pilotService) {
		this.grandPrixService = grandPrixService;
		this.positionService = positionService;
		this.pilotService = pilotService;
	}

	@GetMapping(value = { "/grandprix/{grandPrixId}/ranking/all" })
	public String listPositions(Map<String, Object> model, @PathVariable("grandPrixId") int grandPrixId) {

		List<Position> positionSorted = this.grandPrixService.findAllPositionsByGrandPrixId(grandPrixId);

		GrandPrix gp = this.grandPrixService.findGPById(grandPrixId);
		model.put("positions", positionSorted);
		model.put("grandprix", gp);
		return "rankings/list";
	}

	@GetMapping(value = { "/grandprix/{grandPrixId}/ranking/new" })
	public String showAllTournaments(ModelMap model, @PathVariable("grandPrixId") int grandPrixId) {

		Set<Pilot> allPilots = this.grandPrixService.findAllPilotsByGrandPrixId(grandPrixId);

		Set<Position> positions = new HashSet<>();

		for (Pilot p : allPilots) {

			Position pos = new Position();
			pos.setPilot(p);
			positions.add(pos);

		}

		GrandPrix gp = this.grandPrixService.findGPById(grandPrixId);
		gp.setPositions(positions);
		model.put("grandprix", gp);
		return "rankings/create";
	}

	@PostMapping(value = "/grandprix/{grandPrixId}/ranking/new")
	public String processCreationForm(@Valid GrandPrix grandprix, BindingResult result, Position position,
			ModelMap model, @PathVariable("grandPrixId") int grandPrixId, HttpServletRequest request)
			throws DataAccessException {
		if (result.hasErrors()) {
			System.out.println(result.getFieldError());
			Set<Pilot> allPilots = this.grandPrixService.findAllPilotsByGrandPrixId(grandPrixId);

			Set<Position> positions = new HashSet<>();

			for (Pilot p : allPilots) {

				Position pos = new Position();
				pos.setPilot(p);
				positions.add(pos);

			}
			grandprix.setPositions(positions);
			model.put("grandprix", grandprix);
			model.put("message", "All positions are required");
			return "rankings/create";
		} else {

			Set<Position> positions = buildPositions(request);

			if (positions.size() == 0) {

				model.put("message", "positions has to be between 1 and 20");
				Set<Pilot> allPilots = this.grandPrixService.findAllPilotsByGrandPrixId(grandPrixId);

				positions = new HashSet<>();

				for (Pilot p2 : allPilots) {

					Position pos = new Position();
					pos.setPilot(p2);
					positions.add(pos);

				}
				grandprix.setPositions(positions);
				model.put("grandprix", grandprix);
				return "rankings/create";

			}

			Set<Integer> posSet = new HashSet<>();
			List<Integer> posList = new ArrayList<Integer>();

			for (Position p : positions) {

				posSet.add(p.getPos());
				posList.add(p.getPos());

			}

			if (posSet.size() != posList.size()) {

				grandprix.setPositions(positions);

				model.put("message", "There can't be repeted positions");
				model.put("alert", "There can't be repeted positions");
				model.put("grandprix", grandprix);
				return "rankings/create";

			}

			for (Position p : positions) {

				try {
					this.positionService.savePosition(p);
				} catch (ConstraintViolationException ex) {

					model.put("message", "positions has to be between 1 and 20");
					Set<Pilot> allPilots = this.grandPrixService.findAllPilotsByGrandPrixId(grandPrixId);

					positions = new HashSet<>();

					for (Pilot p2 : allPilots) {

						Position pos = new Position();
						pos.setPilot(p2);
						positions.add(pos);

					}
					grandprix.setPositions(positions);
					model.put("grandprix", grandprix);
					return "rankings/create";

				}

			}

			GrandPrix gp = this.grandPrixService.findGPById(grandPrixId);
			Set<Pilot> pilots = gp.getPilots();
			Set<Team> teams = gp.getTeam();
			grandprix.setPositions(positions);
			grandprix.setPilots(pilots);
			grandprix.setTeam(teams);
			grandprix.setId(gp.getId());
			grandprix.setDayOfRace(gp.getDayOfRace());

			this.grandPrixService.save(grandprix);

			return "redirect:/grandprix/all";
		}
	}

	private Set<Position> buildPositions(HttpServletRequest request) {
		Set<Position> positions = new HashSet<Position>();
		Iterator<String> names = request.getParameterNames().asIterator();
		String name;
		Position p = null;
		Integer pilotId;
		Integer pos;
		while (names.hasNext()) {
			name = names.next();
			try {
				pilotId = Integer.valueOf(name);
				pos = Integer.valueOf(request.getParameter(name));
				p = new Position();
				p.setPilot(pilotService.findById(pilotId));
				p.setPos(pos);
				positions.add(p);
			} catch (Exception e) {
			}
		}
		return positions;
	}

}
