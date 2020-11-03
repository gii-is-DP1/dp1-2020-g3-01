package org.springframework.samples.petclinic.web;

import java.util.Date;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TeamController {

	private final TeamService teamService;
	private final ManagerService managerService;

	@Autowired
	public TeamController(TeamService teamService, ManagerService managerService) {
		this.teamService = teamService;
		this.managerService = managerService;

	}
	
	@ModelAttribute("manager")
	public Manager findManager(@PathVariable("managerId") int managerId) {
		return this.managerService.findManagerById(managerId);
	}
        

	@GetMapping(value = "managers/{managerId}/teams/new")
	public String initCreationForm(Manager manager, ModelMap model) {
		Manager managerRegistered = this.managerService.findOwnerByUserName();
		if(managerRegistered.getId()!=manager.getId()) {
			
			String message = "No seas malo, no puedes crear equipos por otro";
			model.put("customMessage", message);
			return "exception";
		}
		
		Integer countedTeams = this.teamService.countTeams(manager.getId());
		if(countedTeams!=0) {
			
			String message = "Ya has creado un equipo";
			model.put("customMessage", message);
			return "exception";
		}
		
		Team team = new Team();
		
		team.setManager(manager);
		model.put("team", team);
		return "teams/create";
	}

	@PostMapping(value = "managers/{managerId}/teams/new")
	public String processCreationForm(Manager manager, @Valid Team team, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("team", team);
			return "teams/create";
		} else {

			team.setManager(manager);
			Date creation = new Date();
			team.setCreationDate(creation);
			this.teamService.saveTeam(team);
			System.out.println(team);
			return "redirect:/welcome";
		}
	}
}
