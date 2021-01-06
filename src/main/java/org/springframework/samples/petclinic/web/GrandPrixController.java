package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Motorcycle;
import org.springframework.samples.petclinic.model.Pilot;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GrandPrixController {
	
	private static final String VIEWS_GRANDPRIX_CREATE_OR_UPDATE_FORM = "grandprix/createOrUpdateGrandPrix";

	private final GrandPrixService grandPrixService;
	//private final PilotService pilotService;
	//private final TeamService teamService;
	private final UserService userService;
	
	@Autowired
	public GrandPrixController(GrandPrixService grandPrixService, PilotService pilotService,
			TeamService teamService, UserService userService) {
		this.grandPrixService = grandPrixService;
		//this.pilotService = pilotService;
		//this.teamService = teamService;
		this.userService = userService;
	}
	
	//Create
	@GetMapping(value = "/admin/grandprix/new")
	public String initCreationForm(ModelMap model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> user = this.userService.findUser(username);
        Optional<User> admin1 = this.userService.findUser("admin1");
        if(user.get() != admin1.get()) {
        	String message = "No seas malo, no puedes crear un gran premio";
			model.put("customMessage", message);
			return "exception";
        }
        
        GrandPrix gp = new GrandPrix();
		model.put("grandPrix", gp);
		return VIEWS_GRANDPRIX_CREATE_OR_UPDATE_FORM;		
	}
	
	@PostMapping(value = "/admin/grandprix/new")
	public String processCreationForm(@Valid GrandPrix grandPrix, BindingResult result,
			ModelMap model) throws DataAccessException {
		if (result.hasErrors()) {
			model.put("grandPrix", grandPrix);
			return VIEWS_GRANDPRIX_CREATE_OR_UPDATE_FORM;
		} else {
			this.grandPrixService.saveGP(grandPrix);
			int id = grandPrix.getId();
			return "redirect:/admin/grandprix/" + id + "/details";
		}
	}
	

}
