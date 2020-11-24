package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Motorcycle;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.MotorcycleService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MotorcycleController {
	
	private static final String VIEWS_MOTORCYCLES_CREATE_OR_UPDATE_FORM = "teams/createOrUpdateBikeForm";
	
	private final MotorcycleService motorcycleService;
	private final ManagerService managerService;
	private final TeamService teamService;

	@Autowired
	public MotorcycleController(MotorcycleService motorcycleService, ManagerService managerService, TeamService teamService) {
		this.motorcycleService = motorcycleService;
		this.managerService = managerService;
		this.teamService = teamService;
	}
	
	@GetMapping("motorcycle/{motorcycleId}/details")
	public String showMotorcycle(@PathVariable("motorcycleId") int motorcycleId, ModelMap model) {
		Motorcycle motorcycle = this.motorcycleService.findMotorcycleById(motorcycleId);
		model.put("motorcycle", motorcycle);
		return "motorcycle/motorcycleDetails";
	}
	
	
	@GetMapping(value = "/managers/{managerId}/teams/{teamId}/pilot/{pilotId}/bikes/new")
	public String initCreationForm(@PathVariable("managerId") int managerId, @PathVariable("teamId") int teamId, @PathVariable("pilotId") int pilotId, ModelMap model) {
		Manager managerRegistered = this.managerService.findOwnerByUserName();
		if (managerRegistered.getId() != managerId) {

			String message = "No seas malo, no puedes crear una moto por otro";
			model.put("customMessage", message);
			return "exception";
		}

		Integer countedBikes = this.motorcycleService.countBikes(pilotId);
		if (countedBikes == 1) {

			String message = "El piloto ya tiene una moto";
			model.put("customMessage", message);
			return "exception";
		}

		Motorcycle motorcycle = new Motorcycle();
		//Pilot pilot = this.managerService.find;
		//pilot.set(manager);
		model.put("motorcycle", motorcycle);
		return VIEWS_MOTORCYCLES_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/managers/{managerId}/teams/{teamId}/pilot/{pilotId}/bikes/new")
	public String processCreationForm(@Valid Motorcycle motorcycle, BindingResult result, @PathVariable("pilotId") int pilotId,  ModelMap model) throws DataAccessException{
		if (result.hasErrors()) {
			model.put("motorcycle", motorcycle);
			return VIEWS_MOTORCYCLES_CREATE_OR_UPDATE_FORM;
		} else {
			Pilot piloto = this.teamService.searchPilot(pilotId);
			motorcycle.setPilot(piloto);
			this.motorcycleService.saveMoto(motorcycle);
			int id = motorcycle.getId();
			return "redirect:/motorcycle/" + id +"/details";
		}
	}

















}
