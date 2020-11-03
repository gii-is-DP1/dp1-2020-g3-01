package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Manager;
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

	@Autowired
	public ManagerController(ManagerService managerService) {
		this.managerService = managerService;

	}
	
	@GetMapping("managers/details")
	public String showOwner(ModelMap model) {
		Manager manager = this.managerService.findOwnerByUserName();
		model.put("manager", manager);
		return "managers/managerDetails";
	}

}
