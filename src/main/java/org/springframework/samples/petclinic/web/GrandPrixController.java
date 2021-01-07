package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.GrandPrixService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GrandPrixController {

	private static final String VIEWS_GRANDPRIX_CREATE_OR_UPDATE_FORM = "grandprix/createOrUpdateGrandPrix";

	private final GrandPrixService grandPrixService;
	// private final PilotService pilotService;
	// private final TeamService teamService;
	private final UserService userService;

	@Autowired
	public GrandPrixController(GrandPrixService grandPrixService, PilotService pilotService, TeamService teamService,
			UserService userService) {
		this.grandPrixService = grandPrixService;
		// this.pilotService = pilotService;
		// this.teamService = teamService;
		this.userService = userService;
	}

	// Show list of grand prixes

	@GetMapping(value = { "/grandprix/all" })
	public String showAllTournaments(Map<String, Object> model) {

		Collection<GrandPrix> allGrandPrix = this.grandPrixService.findAll();
		model.put("grandPrix", allGrandPrix);
		return "grandprix/list";
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

}
