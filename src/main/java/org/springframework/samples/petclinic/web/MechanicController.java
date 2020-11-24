package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.samples.petclinic.service.MechanicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MechanicController {
	
	private final MechanicService mechanicService;

	@Autowired
	public MechanicController(MechanicService mechanicService) {
		this.mechanicService = mechanicService;

	}
	
	@GetMapping("managers/{managerId}/teams/{teamId}/mechanics/{mechanicId}/details")
	public String showTeam(@PathVariable("mechanicId") int mechanicId,ModelMap model) {
		
		Mechanic mechanic = this.mechanicService.findMechanicById(mechanicId);
		model.put("mechanic", mechanic);
		return "mechanics/mechanicDetails";
	}

}
