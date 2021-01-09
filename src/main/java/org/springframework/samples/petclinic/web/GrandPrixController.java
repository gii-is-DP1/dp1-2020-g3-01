package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Forum;
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Motorcycle;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.GrandPrixService;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.MotorcycleService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GrandPrixController {

	private static final String VIEWS_GRANDPRIX_CREATE_OR_UPDATE_FORM = "grandprix/createOrUpdateGrandPrix";

	private final GrandPrixService grandPrixService;
	// private final PilotService pilotService;
	private final TeamService teamService;
	private final UserService userService;
	private final ManagerService managerService;
	private final MotorcycleService motorcycleService;

	@Autowired
	public GrandPrixController(GrandPrixService grandPrixService, PilotService pilotService, TeamService teamService,
			UserService userService, ManagerService managerService, MotorcycleService motorcycleService) {
		this.grandPrixService = grandPrixService;
		// this.pilotService = pilotService;
		this.teamService = teamService;
		this.userService = userService;
		this.managerService = managerService;
		this.motorcycleService = motorcycleService;
	}


	// Show list of grand prixes

	@GetMapping(value = { "/grandprix/all" })
	public String showAllTournaments(Map<String, Object> model) {

		Collection<GrandPrix> allGrandPrix = this.grandPrixService.findAll();
		model.put("grandPrix", allGrandPrix);
		return "grandprix/list";
	}
	
	// Show grand prix details
	
	@GetMapping(value = "grandprix/{grandPrixId}/details")
	public String showGrandPrix(@PathVariable("grandPrixId") int grandPrixId, ModelMap model) {
		GrandPrix gp = this.grandPrixService.findGPById(grandPrixId);
		model.put("grandPrix", gp);
		return "grandprix/grandPrixDetails";
	}

	// Create
	@GetMapping(value = "/grandprix/new")
	public String initCreationForm(ModelMap model) {
		GrandPrix gp = new GrandPrix();
		model.put("grandPrix", gp);
		return VIEWS_GRANDPRIX_CREATE_OR_UPDATE_FORM;
	}
	

	@PostMapping(value = "/grandprix/new")
	public String processCreationForm(@Valid GrandPrix grandPrix, BindingResult result, ModelMap model)
			throws DataAccessException {
		if (result.hasErrors()) {
			model.put("grandPrix", grandPrix);
			return VIEWS_GRANDPRIX_CREATE_OR_UPDATE_FORM;
		} else {
			this.grandPrixService.saveGP(grandPrix);
			//int id = grandPrix.getId();
			return "redirect:/grandprix/all";
		}
	}
	
	//Inscribir un equipo en una carrera
	@GetMapping(value = "/grandprix/{grandPrixId}/addTeam")
	public String AddTeamInGP(@PathVariable("grandPrixId") int grandPrixId, ModelMap model) {
		Manager registeredManager = this.managerService.findOwnerByUserName();
		Team team = this.teamService.findManager(registeredManager.getId());
		if(team.getManager().getId()!=registeredManager.getId()) {
			model.put("message", "No seas malo, no puedes inscribir un equipo que no sea tuyo a una carrera");
			return "exception";
		}
		Set<Pilot> pilotos = team.getPilot();
		if(pilotos.size()<1) {
			model.put("message", "No puedes inscribir un equipo sin pilotos en una carrera!");
			return "exception";
		} else {
				for(Pilot i: pilotos) {
					try {
						Motorcycle m = this.motorcycleService.findMotorcycleByPilotId(i.getId());
						m.getBrand();
					} catch(NullPointerException exception) {
						model.put("message", "El piloto: " + i.getFirstName() + " " + i.getLastName() + " no tiene ninguna moto asociada!");
						return "exception";
					}
				}
		}
		GrandPrix gp = this.grandPrixService.findGPById(grandPrixId);
		if(gp.getTeam().contains(team)) {
			model.put("message", "Tu equipo ya esta inscrito en esta carrera!");
			return "exception";
		}
		if(gp.getTeam().size()>=10) {
			model.put("message", "No pueden escribirse mas equipos, el maximo es 10!");
			return "exception";
		}
		model.put("grandPrix", gp);
		Set<Team> set = gp.getTeam();
		set.add(team);
		gp.setTeam(set);
		this.grandPrixService.saveGP(gp);
		return "redirect:/welcome";
	}
	
	//Eliminar un equipo en una carrera
	@GetMapping(value = "/grandprix/{grandPrixId}/removeTeam")
	public String removeTeamInGP(@PathVariable("grandPrixId") int grandPrixId, ModelMap model) {
		Manager registeredManager = this.managerService.findOwnerByUserName();
		Team team = this.teamService.findManager(registeredManager.getId());
		if(team.getManager().getId()!=registeredManager.getId()) {
			model.put("message", "No seas malo, no puedes eliminar un equipo que no sea tuyo de una carrera");
			return "exception";
		}
		GrandPrix gp = this.grandPrixService.findGPById(grandPrixId);
		if(!gp.getTeam().contains(team)) {
			model.put("message", "Tu equipo no está inscrito en esta carrera!");
			return "exception";
		}
		model.put("grandPrix", gp);
		Set<Team> set = gp.getTeam();
		set.remove(team);
		gp.setTeam(set);
		this.grandPrixService.saveGP(gp);
		return "redirect:/welcome";
	}

}
