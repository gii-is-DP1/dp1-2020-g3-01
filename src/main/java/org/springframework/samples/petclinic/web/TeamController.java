package org.springframework.samples.petclinic.web;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.List;
import java.util.regex.Pattern;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.Type;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.MechanicService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TeamController {

	private final TeamService teamService;
	private final ManagerService managerService;
	private final MechanicService mechanicService;

	@Autowired
	public TeamController(TeamService teamService, ManagerService managerService, MechanicService mechanicService) {
		this.teamService = teamService;
		this.managerService = managerService;
		this.mechanicService = mechanicService;

	}
	
//	@ModelAttribute("manager")
//	public Manager findManager(@PathVariable("managerId") int managerId) {
//		return this.managerService.findManagerById(managerId);
//	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("mechanic")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new MechanicValidator());
	}
	
//	@ModelAttribute("team")
//	public Team findTeam(@PathVariable("teamId") int teamId) {
//		return this.teamService.findTeamById(teamId);
//	}

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
	public String processCreationForm( @Valid Team team, @PathVariable("managerId") int managerId, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("team", team);
			return "teams/create";
		} else {

			team.setManager(this.managerService.findManagerById(managerId));
			Date creation = new Date();
			team.setCreationDate(creation);
			this.teamService.saveTeam(team);
			System.out.println(team);
			return "redirect:/welcome";
		}
	}
	
	@GetMapping(value = "teams/{teamId}/mechanics/new")
	public String mechanicCreationForm(@PathVariable("teamId") int teamId, ModelMap model) {
		
		Manager managerRegistered = this.managerService.findOwnerByUserName();
		Manager managerTeam = this.teamService.findTeamById(teamId).getManager();
		if(managerRegistered.getId()!=managerTeam.getId()) {
			
			String message = "No seas malo, no puedes crear mecánicos por otro";
			model.put("customMessage", message);
			return "exception";
		}
		
		Mechanic mechanic = new Mechanic();
		Set<Mechanic> mechanics = this.teamService.getMechanicsById(this.teamService.findTeamById(teamId).getId());
//		Set<Mechanic> mechanics = new HashSet<>();
		Type[] typesArray = Type.values();
		List<Type> types = Arrays.asList(typesArray);
		model.put("types", types);
		mechanics.add(mechanic);
		this.teamService.findTeamById(teamId).setMechanic(mechanics);
		model.put("mechanic", mechanic);
		return "mechanics/createOrUpdateMechanicForm";
	}
	
	@PostMapping(value = "teams/{teamId}/mechanics/new")
	public String processCreationForm(@PathVariable("teamId") int teamId,@Valid Mechanic mechanic, BindingResult result, ModelMap model) throws DataAccessException{
		if (result.hasErrors()) {
			model.put("mechanic", mechanic);
			return "mechanics/createOrUpdateMechanicForm";
		} else {

			Set<Mechanic> mechanics = this.teamService.getMechanicsById(teamId);
			mechanics.add(mechanic);
			Team team = this.teamService.findTeamById(teamId);
			team.setMechanic(mechanics);
			this.mechanicService.saveMechanic(mechanic);
			System.out.println(team);
			return "redirect:/welcome";
		}
	}
}
