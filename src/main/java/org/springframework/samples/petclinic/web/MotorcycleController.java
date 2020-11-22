//package org.springframework.samples.petclinic.web;
//
//import java.util.Date;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.samples.petclinic.model.Manager;
//import org.springframework.samples.petclinic.model.Motorcycle;
//import org.springframework.samples.petclinic.model.Pilot;
//import org.springframework.samples.petclinic.model.Team;
//import org.springframework.samples.petclinic.service.ManagerService;
//import org.springframework.samples.petclinic.service.MotorcycleService;
//import org.springframework.samples.petclinic.service.TeamService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Controller
//public class MotorcycleController {
//	
//	private static final String VIEWS_MOTORCYCLES_CREATE_OR_UPDATE_FORM = "teams/createOrUpdateMotorcycleForm";
//	
//	private final MotorcycleService motorcycleService;
//	private final ManagerService managerService;
//
//	@Autowired
//	public MotorcycleController(MotorcycleService motorcycleService, ManagerService managerService) {
//		this.motorcycleService = motorcycleService;
//		this.managerService = managerService;
//	}
//	
//	@GetMapping(value = "/pilot/{pilotId}/bikes/new")
//	public String initCreationForm(Manager manager, ModelMap model) {
//		Manager managerRegistered = this.managerService.findOwnerByUserName();
//		if (managerRegistered.getId() != manager.getId()) {
//
//			String message = "No seas malo, no puedes crear equipos por otro";
//			model.put("customMessage", message);
//			return "exception";
//		}
//
//		Integer countedBikes = this.motorcycleService.countBikes(manager.getId());
//		if (countedBikes != 0) {
//
//			String message = "El piloto ya tiene una moto";
//			model.put("customMessage", message);
//			return "exception";
//		}
//
//		Motorcycle motorcycle = new Motorcycle();
//		Pilot pilot = this.managerService.find;
//
//		pilot.set(manager);
//		model.put("pilot", pilot);
//		return VIEWS_MOTORCYCLES_CREATE_OR_UPDATE_FORM;
//	}
//
//	@PostMapping(value = "/managers/{managerId}/teams/pilot/{pilotId}/bikes/new")
//	public String processCreationForm(Manager manager, @Valid Motorcycle motorcycle, Pilot pilot, BindingResult result, ModelMap model) {
//		if (result.hasErrors()) {
//			model.put("pilot", pilot);
//			return VIEWS_MOTORCYCLES_CREATE_OR_UPDATE_FORM;
//		} else {
//
//			motorcycle.setPilot(pilot);
//			this.motorcycleService.saveMoto(motorcycle);
//			System.out.println(motorcycle);
//			return "redirect:/welcome";
//		}
//	}
//
//}
>>>>>>> Stashed changes
