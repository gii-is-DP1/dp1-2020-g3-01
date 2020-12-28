package org.springframework.samples.petclinic.web;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.RollbackException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Forum;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.Type;
import org.springframework.samples.petclinic.service.ForumService;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.MechanicService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPersonDni;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TeamController {

	private final TeamService teamService;
	private final ManagerService managerService;
	private final MechanicService mechanicService;
	private final ForumService forumService;


	private static final String VIEWS_TEAMS_CREATE_OR_UPDATE_FORM = "teams/createOrUpdateTeamForm";

	@Autowired
	public TeamController(TeamService teamService, ManagerService managerService, MechanicService mechanicService,
			ForumService forumService) {
		this.teamService = teamService;
		this.managerService = managerService;
		this.mechanicService = mechanicService;
		this.forumService = forumService;

	}

//	@ModelAttribute("manager")
//	public Manager findManager(@PathVariable("managerId") int managerId) {
//		return this.managerService.findManagerById(managerId);
//	}

	@GetMapping("managers/{managerId}/teams/{teamId}/details")
	public String showTeam(@PathVariable("managerId") int managerId, @PathVariable("teamId") int teamId,
			ModelMap model) {
		Team team = this.teamService.findTeamById(teamId);
		System.out.println(team);
		model.put("team", team);
		Forum hasForum = this.forumService.findForumByTeamId(teamId);
		if (hasForum == null) {
			model.put("hasForum", true);
		}else {
			model.put("hasForum", false);
		}
		return "teams/teamDetails";
	}

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
	public String initCreationForm(ModelMap model, @PathVariable("managerId") int managerId) {
		Manager managerRegistered = this.managerService.findOwnerByUserName();
		if (managerRegistered.getId() != managerId) {

			String message = "No seas malo, no puedes crear equipos por otro";
			model.put("customMessage", message);
			return "exception";
		}

		Integer countedTeams = this.teamService.countTeams(managerId);
		if (countedTeams != 0) {

			String message = "Ya has creado un equipo";
			model.put("customMessage", message);
			return "exception";
		}

		Team team = new Team();

		Manager manager = this.managerService.findManagerById(managerId);
		team.setManager(manager);
		model.put("team", team);
		return "teams/createOrUpdateTeamForm";
	}

	@PostMapping(value = "managers/{managerId}/teams/new")
	public String processCreationForm(@Valid Team team, BindingResult result, @PathVariable("managerId") int managerId,
			ModelMap model) throws DataIntegrityViolationException
	{
		if (result.hasErrors()) {
			model.put("team", team);
			return "teams/createOrUpdateTeamForm";
		} else {

			team.setManager(this.managerService.findManagerById(managerId));
			Date creation = new Date();
			team.setCreationDate(creation);

			try {
				this.teamService.saveTeam(team);
			} catch (DataIntegrityViolationException ex) {

				String s = ex.getMessage();
				s = s.substring(s.indexOf("(") + 1);
				s = s.substring(0, s.indexOf(")"));
//				String atribute = message[1].split(")")[0];
				s =  s.toLowerCase();				
				result.rejectValue(s, "duplicate", "already exists");
				return "teams/createOrUpdateTeamForm";

			}
			
			System.out.println(team);
			return "redirect:/welcome";
		}
	}

	@GetMapping(value = "managers/{managerId}/teams/{teamId}/mechanics/new")
	public String mechanicCreationForm(@PathVariable("teamId") int teamId, ModelMap model) {

		Manager managerRegistered = this.managerService.findOwnerByUserName();
		Manager managerTeam = this.teamService.findTeamById(teamId).getManager();
		if (managerRegistered.getId() != managerTeam.getId()) {

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

	@PostMapping(value = "managers/{managerId}/teams/{teamId}/mechanics/new")
	public String processCreationForm(@PathVariable("teamId") int teamId, @Valid Mechanic mechanic,
			BindingResult result, ModelMap model) throws DataAccessException, DuplicatedPersonDni {
		if (result.hasErrors()) {
			model.put("mechanic", mechanic);
			Type[] typesArray = Type.values();
			List<Type> types = Arrays.asList(typesArray);
			model.put("types", types);
			return "mechanics/createOrUpdateMechanicForm";
		} else {

			Set<Mechanic> mechanics = this.teamService.getMechanicsById(teamId);
			mechanics.add(mechanic);
			Team team = this.teamService.findTeamById(teamId);
			team.setMechanic(mechanics);
			
			try {
				this.mechanicService.saveMechanic(mechanic);
			} catch (DataIntegrityViolationException ex) {

				String s = ex.getMessage();
				s = s.substring(s.indexOf("(") + 1);
				s = s.substring(0, s.indexOf(")"));
//				String atribute = message[1].split(")")[0];
				s =  s.toLowerCase();				
				result.rejectValue(s, "duplicate", "already exists");
				return "mechanics/createOrUpdateMechanicForm";

			}


			System.out.println(team);
			return "redirect:/welcome";
		}
	}

	@GetMapping(value = "/managers/{managerId}/teams/{teamId}/remove")
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

	@GetMapping(value = "/managers/{managerId}/teams/{teamId}/edit")
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

	@PostMapping(value = "/managers/{managerId}/teams/{teamId}/edit")
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
			Set<Pilot> pilots = this.teamService.getPilotsById(team.getId());
			Set<Mechanic> mechanics = this.teamService.getMechanicsById(team.getId());
			team.setManager(manager);
			team.setMechanic(mechanics);
			team.setPilot(pilots);


			try {
				this.teamService.saveTeam(team);
			} catch (DataIntegrityViolationException ex) {

				String s = ex.getMessage();
				s = s.substring(s.indexOf("(") + 1);
				s = s.substring(0, s.indexOf(")"));
//				String atribute = message[1].split(")")[0];
				s =  s.toLowerCase();				
				result.rejectValue(s, "duplicate", "already exists");
				return "teams/createOrUpdateTeamForm";

			}

			return "redirect:/managers/" + managerId + "/teams/" + team.getId() + "/details";
			// Aqui deberia redirigir a la vista de detalles del team
		}
	}

}
