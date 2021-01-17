package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
	public RankingController(GrandPrixService grandPrixService,PositionService positionService,PilotService pilotService) {
		this.grandPrixService = grandPrixService;
		this.positionService = positionService;
		this.pilotService = pilotService;
		// this.teamService = teamService;
		// this.userService = userService;
	}
	
	
	@GetMapping(value = { "/grandprix/{grandPrixId}/ranking/all" })
	public String listPositions(Map<String, Object> model, @PathVariable("grandPrixId") int grandPrixId) {

		Set<Position> positions = this.grandPrixService.findAllPositionsByGrandPrixId(grandPrixId);
		
		List<Position> positionSorted = new ArrayList<>();
		for(Position p : positions) {
			positionSorted.add(p);
		}
		
		Collections.sort(positionSorted , (o1, o2) -> o1.getPos().compareTo(o2.getPos()));
		
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
		//this.grandPrixService.saveGP(gp);
		model.put("grandprix", gp);
		return "rankings/create";
	}

	@PostMapping(value = "/grandprix/{grandPrixId}/ranking/new")
	public String processCreationForm(@Valid GrandPrix grandprix, BindingResult result, Position position,  ModelMap model,@PathVariable("grandPrixId") int grandPrixId, HttpServletRequest request)
			throws DataAccessException {
		if (result.hasErrors()) {
			System.out.println(result.getFieldError());
			model.put("grandprix", grandprix);
			return "rankings/create";
		} else {
			
			Set<Position> positions = buildPositions(request);
			
			Set<Integer> posSet = new HashSet<>();
			List<Integer> posList = new ArrayList<Integer>();
			
			for(Position p:positions) {
				
				posSet.add(p.getPos());
				posList.add(p.getPos());
				
			}
			
			if(posSet.size()!=posList.size()) {
				
				grandprix.setPositions(positions);
				model.put("grandprix", grandprix);
				model.put("alert", true);
				return "rankings/create";
				
			}
			
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
				
				this.positionService.savePosition(p);

			}
			
			GrandPrix gp = this.grandPrixService.findGPById(grandPrixId);
			Set<Pilot> pilots  = gp.getPilots(); 
			Set<Team> teams  = gp.getTeam();
			grandprix.setPositions(positions);
			grandprix.setPilots(pilots);
			grandprix.setTeam(teams);
			grandprix.setId(gp.getId());
			grandprix.setDayOfRace(gp.getDayOfRace());
			//System.out.println(NullPointerException.);
			this.grandPrixService.saveGP(grandprix);
			return "redirect:/grandprix/all";
		}
	}

	private Set<Position> buildPositions(HttpServletRequest request) {
		Set<Position> positions=new HashSet<Position>();
		Iterator<String> names=request.getParameterNames().asIterator();
		String name;
		Position p=null;
		Integer pilotId;
		Integer pos;
		while(names.hasNext()) {
			name=names.next();
			try {	
				pilotId=Integer.valueOf(name);
				pos=Integer.valueOf(request.getParameter(name));
				p=new Position();
				p.setPilot(pilotService.findById(pilotId));
				p.setPos(pos);
				positions.add(p);
			}catch(Exception e) {}
		}
		return positions;
	}

}
