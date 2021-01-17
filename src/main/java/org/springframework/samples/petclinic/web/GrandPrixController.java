package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.GrandPrixService;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.exceptions.MaxTeamsException;
import org.springframework.samples.petclinic.service.exceptions.NoPilotsException;
import org.springframework.samples.petclinic.service.exceptions.PilotWithoutBikeException;
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
	private final ManagerService managerService;

	@Autowired
	public GrandPrixController(GrandPrixService grandPrixService, PilotService pilotService, TeamService teamService,
			ManagerService managerService) {
		this.grandPrixService = grandPrixService;
		// this.pilotService = pilotService;
		this.teamService = teamService;
		this.managerService = managerService;
	}
	
	@InitBinder("grandPrix")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new GrandPrixValidator());
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
			throws DataAccessException, NoPilotsException, PilotWithoutBikeException, MaxTeamsException {
		if (result.hasErrors()) {
			model.put("grandPrix", grandPrix);
			return VIEWS_GRANDPRIX_CREATE_OR_UPDATE_FORM;
		} else {
			try {
				this.grandPrixService.save(grandPrix);
			} catch (DataIntegrityViolationException ex) {

				String s = ex.getMessage();
				s = s.substring(s.indexOf("(") + 1);
				s = s.substring(0, s.indexOf(")"));
				s = s.toLowerCase();
				result.rejectValue(s, "duplicate", "already exists");
				return VIEWS_GRANDPRIX_CREATE_OR_UPDATE_FORM;

			}
		}
		return "redirect:/grandprix/all";
	}

	// Editar gran premio

	@GetMapping(value = "/grandprix/{grandPrixId}/edit")
	public String initUpdateForm(@PathVariable("grandPrixId") int grandPrixId, ModelMap model)
			throws DataAccessException {
		GrandPrix gp = this.grandPrixService.findGPById(grandPrixId);
		model.put("grandPrix", gp);
		return "/grandprix/createOrUpdateGrandPrix";
	}

	@PostMapping(value = "/grandprix/{grandPrixId}/edit")
	public String processUpdateForm(@Valid GrandPrix gp, BindingResult result, ModelMap model,
			@PathVariable("grandPrixId") int gpId) throws DataAccessException {
		if (result.hasErrors()) {
			model.put("grandPrix", gp);
			return "/grandprix/createOrUpdateGrandPrix";
		} else {
			GrandPrix grandPrix = this.grandPrixService.findGPById(gpId);
			grandPrix = gp;
			grandPrix.setId(gpId);
			try {
				this.grandPrixService.save(grandPrix);
			} catch (DataIntegrityViolationException ex) {

				String s = ex.getMessage();
				s = s.substring(s.indexOf("(") + 1);
				s = s.substring(0, s.indexOf(")"));
				s = s.toLowerCase();
				result.rejectValue(s, "duplicate", "already exists");
				return VIEWS_GRANDPRIX_CREATE_OR_UPDATE_FORM;

			}
			return "redirect:/grandprix/{grandPrixId}/details";
		}
	}

	// Eliminar gran premio

	@GetMapping(value = "/grandprix/{grandPrixId}/remove")
	public String delete(@PathVariable("grandPrixId") int grandPrixId, ModelMap model) throws DataAccessException {
		GrandPrix gp = this.grandPrixService.findGPById(grandPrixId);
		this.grandPrixService.removeGP(gp);
		return "redirect:/grandprix/all";
	}

	// Inscribir un equipo en una carrera
	@GetMapping(value = "/grandprix/{grandPrixId}/addTeam")
	public String AddTeamInGP(@PathVariable("grandPrixId") int grandPrixId, ModelMap model)
			throws DataAccessException, NoPilotsException, PilotWithoutBikeException, MaxTeamsException {
		Manager registeredManager = this.managerService.findOwnerByUserName();
		Team team = this.teamService.findManager(registeredManager.getId());
		if (team.getManager().getId() != registeredManager.getId()) {
			model.put("message", "No seas malo, no puedes inscribir un equipo que no sea tuyo a una carrera");
			return "exception";
		}
		GrandPrix gp = this.grandPrixService.findGPById(grandPrixId);
		model.put("grandPrix", gp);
		Set<Team> set = gp.getTeam();
		set.add(team);
		gp.setTeam(set);
		this.grandPrixService.saveGP(gp, team);
		return "redirect:/welcome";
	}

	// Eliminar un equipo en una carrera
	@GetMapping(value = "/grandprix/{grandPrixId}/removeTeam")
	public String removeTeamInGP(@PathVariable("grandPrixId") int grandPrixId, ModelMap model)
			throws DataAccessException, NoPilotsException, PilotWithoutBikeException, MaxTeamsException {
		Manager registeredManager = this.managerService.findOwnerByUserName();
		Team team = this.teamService.findManager(registeredManager.getId());
		if (team.getManager().getId() != registeredManager.getId()) {
			model.put("message", "No seas malo, no puedes eliminar un equipo que no sea tuyo de una carrera");
			return "exception";
		}
		GrandPrix gp = this.grandPrixService.findGPById(grandPrixId);
		if (!gp.getTeam().contains(team)) {
			model.put("message", "Tu equipo no está inscrito en esta carrera!");
			return "exception";
		}
		model.put("grandPrix", gp);
		Set<Team> set = gp.getTeam();
		set.remove(team);
		gp.setTeam(set);
		this.grandPrixService.saveGP(gp, team);
		return "redirect:/welcome";
	}

}