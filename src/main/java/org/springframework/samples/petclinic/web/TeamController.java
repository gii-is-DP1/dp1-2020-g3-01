package org.springframework.samples.petclinic.web;

import java.util.Date;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TeamController {

	private static final String VIEWS_TEAMS_CREATE_OR_UPDATE_FORM = "teams/createOrUpdateTeamForm";

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

	@GetMapping(value = "/managers/{managerId}/teams/new")
	public String initCreationForm(Manager manager, ModelMap model) {
		Manager managerRegistered = this.managerService.findOwnerByUserName();
		if (managerRegistered.getId() != manager.getId()) {

			String message = "No seas malo, no puedes crear equipos por otro";
			model.put("customMessage", message);
			return "exception";
		}

		Integer countedTeams = this.teamService.countTeams(manager.getId());
		if (countedTeams != 0) {

			String message = "Ya has creado un equipo";
			model.put("customMessage", message);
			return "exception";
		}

		Team team = new Team();

		team.setManager(manager);
		model.put("team", team);
		return VIEWS_TEAMS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/managers/{managerId}/teams/new")
	public String processCreationForm(Manager manager, @Valid Team team, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("team", team);
			return VIEWS_TEAMS_CREATE_OR_UPDATE_FORM;
		} else {

			team.setManager(manager);
			Date creation = new Date();
			team.setCreationDate(creation);
			this.teamService.saveTeam(team);
			System.out.println(team);
			return "redirect:/welcome";
		}
	}

	@GetMapping(value = "/managers/{managerId}/teams/edit")
	public String initUpdateForm(@PathVariable("managerId") int managerId, ModelMap model) {
		Manager managerRegistered = this.managerService.findOwnerByUserName();
		if (managerRegistered.getId() != managerId) {

			String message = "No seas malo, no puedes editar equipos por otro";
			model.put("customMessage", message);
			return "exception";
		}
		Team team = this.teamService.findManager(managerId);
		model.put("team", team);
		return VIEWS_TEAMS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/managers/{managerId}/teams/edit")
	public String processUpdateForm(@Valid Team team, BindingResult result, @PathVariable("managerId") int managerId,
			ModelMap model) {
		if (result.hasErrors()) {
			model.put("team", team);
			return VIEWS_TEAMS_CREATE_OR_UPDATE_FORM;
		} else {
			Team teamid = this.teamService.findManager(managerId);
			Manager manager = this.managerService.findManagerById(managerId);

			Date fecha = new Date();
			team.setId(teamid.getId());
			team.setCreationDate(fecha);
			team.setManager(manager);

			this.teamService.saveTeam(team);
			return "redirect:/managers/1/teams/edit";
			// Aqui deberia redirigir a la vista de detalles del team
		}
	}

	@GetMapping(value = "/managers/{managerId}/teams/remove")
	public String processDeleteForm(@PathVariable("managerId") int managerId, ModelMap model) {
		Manager managerRegistered = this.managerService.findOwnerByUserName();
		if (managerRegistered.getId() != managerId) {

			String message = "No seas malo, no puedes eliminar equipos por otro";
			model.put("customMessage", message);
			return "exception";
		} else {
			Team team = this.teamService.findManager(managerId);
			this.teamService.removeTeam(team.getId());

			return "redirect:/managers/details";
		}
	}

}
