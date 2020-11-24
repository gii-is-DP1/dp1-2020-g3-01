package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ManagerController {
	
	
	private final ManagerService managerService;
	private final TeamService teamService;

	@Autowired
	public ManagerController(ManagerService managerService, TeamService teamService) {
		this.managerService = managerService;
		this.teamService = teamService;

	}
	
	@GetMapping("managers/details")
	public String showOwner(ModelMap model) {
		Manager manager = this.managerService.findOwnerByUserName();
		Integer hasTeam = this.teamService.countTeams(manager.getId());
		Team team = this.teamService.findManager(manager.getId());
		model.put("manager", manager);
		model.put("team", team);
		if(hasTeam == 1) {
			model.put("hasTeam", false);
		} else {
			model.put("hasTeam", true);
		}
		return "managers/managerDetails";
	}

}
