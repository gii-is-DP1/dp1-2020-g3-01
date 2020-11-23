package org.springframework.samples.petclinic.web;

import java.util.Date;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PilotController {
	
	private final PilotService pilotService;
	private final ManagerService managerService;
	private final TeamService teamService;
	
	@Autowired
	public PilotController(PilotService pilotService, ManagerService managerService, TeamService teamService) {
		this.pilotService = pilotService;
		this.managerService = managerService;
		this.teamService = teamService;

	}
	
	@ModelAttribute("team")
    public Team findTeam(@PathVariable("teamId") int teamId) {
        return this.teamService.findTeamById(teamId);
    }
	
	@ModelAttribute("manager")
    public Manager findById(@PathVariable("managerId") int managerId) {
        return this.managerService.findManagerById(managerId);
    }
	
	@GetMapping(value = "managers/{managerId}/teams/{teamId}/pilots/new")
	public String initCreationForm(Manager manager, Team team, ModelMap model) {
		Manager managerRegistered = this.managerService.findOwnerByUserName();
		if(managerRegistered.getId()!=manager.getId()) {
			String message = "No seas malo, no puedes inscribir pilotos por otro";
			model.put("customMessage", message);
			return "exception";
		}
		
		Pilot pilot = new Pilot();
		
		Set<Pilot> set = team.getPilot();
		set.add(pilot);
		team.setPilot(set);
		model.put("pilot", pilot);
		return "pilots/create";
	}
	
	@GetMapping("managers/{managerId}/pilots/{pilotId}/details")
	public String showPilot(Manager manager, Pilot pilot, ModelMap model) {
		Pilot p = this.pilotService.findById(pilot.getId());
		model.put("pilot", p);
		return "pilots/details";
	}
	
	@PostMapping(value = "managers/{managerId}/teams/{teamId}/pilots/new")
	public String processCreationForm(Manager manager, @Valid Team team, @Valid Pilot pilot, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("pilot", pilot);
			return "pilots/create";
		} else {
			Set<Pilot> set = team.getPilot();
			set.add(pilot);
			team.setPilot(set);
			this.pilotService.savePilot(pilot);
			System.out.println(pilot);
			return "redirect:/welcome";
		}
	}

}
