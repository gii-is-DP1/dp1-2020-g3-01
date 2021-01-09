package org.springframework.samples.petclinic.web;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Position;
import org.springframework.samples.petclinic.service.GrandPrixService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RankingController {

	private final GrandPrixService grandPrixService;

	@Autowired
	public RankingController(GrandPrixService grandPrixService) {
		this.grandPrixService = grandPrixService;
		// this.pilotService = pilotService;
		// this.teamService = teamService;
		// this.userService = userService;
	}

	@GetMapping(value = { "/grandprix/{grandPrixId}/ranking/new" })
	public String showAllTournaments(Map<String, Object> model, @PathVariable("grandPrixId") int grandPrixId) {

		Set<Pilot> allPilots = this.grandPrixService.findAllPilotsByGrandPrixId(grandPrixId);

		Set<Position> positions = new HashSet<>();

		for (Pilot p : allPilots) {

			Position pos = new Position();
			pos.setPilot(p);
			positions.add(pos);

		}

		model.put("positions", positions);
		return "rankings/create";
	}

	@PostMapping(value = "/grandprix/{grandPrixId}/ranking/new")
	public String processCreationForm(@RequestBody Set<Position> positions, BindingResult result, Map<String, Object> model,@PathVariable("grandPrixId") int grandPrixId)
			throws DataAccessException {
		if (result.hasErrors()) {
			model.put("positions", positions);
			return "rankings/create";
		} else {

			for (Position p : positions) {
				switch (p.getPos()) {
				case 1:
					p.setPoint(25);
					break;
				case 2:
					p.setPoint(20);
					break;
				case 3:
					p.setPoint(16);
					break;
				case 4:
					p.setPoint(13);
					break;
				case 5:
					p.setPoint(11);
				case 6:
					p.setPoint(10);
				case 7:
					p.setPoint(9);
					break;
				case 8:
					p.setPoint(8);
					break;
				case 9:
					p.setPoint(7);
					break;
				case 10:
					p.setPoint(6);
					break;
				case 11:
					p.setPoint(5);
					break;
				case 12:
					p.setPoint(4);
					break;
				case 13:
					p.setPoint(3);
					break;
				case 14:
					p.setPoint(2);
					break;
				case 15: 
					p.setPoint(1);
					break;
				case 16:
					p.setPoint(0);
					break;
				case 17:
					p.setPoint(0);
					break;
				case 18:
					p.setPoint(0);
					break;
				case 19:
					p.setPoint(0);
					break;
				case 20:
					p.setPoint(0);
					break;

				}

			}
			
			GrandPrix gp = this.grandPrixService.findGPById(grandPrixId);
			gp.setPositions(positions);
			this.grandPrixService.saveGP(gp);
			return "redirect:/grandprix/all";
		}
	}

}
