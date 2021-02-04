package org.springframework.samples.petclinic.web;

import java.util.Date;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Motorcycle;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.MotorcycleService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.exceptions.TwoMaxPilotPerTeamException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PilotController {
	
	private final PilotService pilotService;
	private final ManagerService managerService;
	private final TeamService teamService;
	private final MotorcycleService motorcycleService;
	
	@InitBinder("pilot")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new PilotValidator());
	}
	
	@Autowired
	public PilotController(PilotService pilotService, ManagerService managerService, TeamService teamService, MotorcycleService motorcycleService) {
		this.pilotService = pilotService;
		this.managerService = managerService;
		this.teamService = teamService;
		this.motorcycleService = motorcycleService;
	}
	
	@ModelAttribute("team")
    public Team findTeam(@PathVariable("teamId") int teamId) {
        return this.teamService.findTeamById(teamId);
    }
	
	@ModelAttribute("manager")
    public Manager findById(@PathVariable("managerId") int managerId) {
        return this.managerService.findManagerById(managerId);
    }
	
	@GetMapping(value = "/managers/{managerId}/teams/{teamId}/pilots/new")
	public String initCreationForm(@PathVariable("teamId") int teamId, @PathVariable("managerId") int managerId, ModelMap model) {
		Manager managerRegistered = this.managerService.findOwnerByUserName();
		//Manager teamManager = this.teamService.findTeamById(teamId).getManager();
		if(managerRegistered.getId()!=managerId) {
			String message = "No seas malo, no puedes inscribir pilotos por otro";
			model.put("customMessage", message);
			return "exception";
		}
		
		Pilot pilot = new Pilot();
		Team t = this.teamService.findTeamById(teamId);
		Set<Pilot> setPilot = t.getPilot();
		setPilot.add(pilot);
		t.setPilot(setPilot);
		model.put("pilot", pilot);
		
		return "pilots/create";
	}
	
	@GetMapping("/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/details")
	public String showPilot(@PathVariable("pilotId") int pilotId, ModelMap model) {
		Pilot p = this.pilotService.findById(pilotId);
		Motorcycle m = this.motorcycleService.findMotorcycleByPilotId(pilotId);
		model.put("pilot", p);
		model.put("motorcycle", m);
		return "pilots/details";
	}
	
	
	@PostMapping(value = "/managers/{managerId}/teams/{teamId}/pilots/new")
	public String processCreationForm(@PathVariable("teamId")int teamId, @Valid Pilot pilot, BindingResult result, ModelMap model) throws DataAccessException {
		if (result.hasErrors()) {
			model.put("pilot", pilot);
			return "pilots/create";
		} else {
			try {
				Team t = this.teamService.findTeamById(teamId);
				Set<Pilot> set = t.getPilot();
				set.add(pilot);
				t.setPilot(set);
				this.pilotService.savePilot(pilot, t);
			} catch(TwoMaxPilotPerTeamException ex) {
				model.put("customMessage", "Un equipo no puede tener más de dos pilotos");
				return "exception";
			} catch (DataIntegrityViolationException ex) {

				String s = ex.getMessage();
				s = s.substring(s.indexOf("(") + 1);
				s = s.substring(0, s.indexOf(")"));
				s =  s.toLowerCase();				
				result.rejectValue(s, "duplicate", "already exists");
				return "pilots/create";

			}
			return "redirect:/welcome";
		}
	}
	
	@GetMapping(value = "/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/remove")
	public String processDeleteForm(@PathVariable("managerId") int managerId,@PathVariable("teamId") int teamId,@PathVariable("pilotId") int pilotId, ModelMap model) throws DataAccessException {
		Manager managerRegistered = this.managerService.findOwnerByUserName();
		if (managerRegistered.getId() != managerId) {

			String message = "No seas malo, no puedes eliminar pilotos por otro";
			model.put("customMessage", message);
			return "exception";
		} else {
			Pilot p = this.pilotService.findById(pilotId);
			Team t = this.teamService.findTeamById(teamId);
			t.getPilot().remove(p);
			this.teamService.saveTeam(t);
			this.pilotService.removePilot(p.getId());
			
			return "redirect:/welcome";
		}
	}
	
	@GetMapping(value = "/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/update")
	public String initUpdateForm(@PathVariable("managerId") int managerId,@PathVariable("teamId") int teamId
			,@PathVariable("pilotId") int pilotId, ModelMap model) {
		Manager managerRegistered = this.managerService.findOwnerByUserName();
		if (managerRegistered.getId() != managerId) {

			String message = "No seas malo, no puedes editar pilotos por otro";
			model.put("customMessage", message);
			return "exception";
		}
		Pilot p = this.pilotService.findById(pilotId);
		model.put("pilot", p);
		return "/pilots/create";
	}
	
	@PostMapping(value = "/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/update")
	public String processUpdateForm(@Valid Pilot pilot, BindingResult result, @PathVariable("managerId") int managerId,
			@PathVariable("teamId") int teamId,@PathVariable("pilotId") int pilotId, ModelMap model) throws DataAccessException, TwoMaxPilotPerTeamException {
		if (result.hasErrors()) {
			model.put("pilot", pilot);
			return "pilots/create";
		} else {
			try {
				Pilot p = this.pilotService.findById(pilotId);
				p = pilot;
				p.setId(pilotId);
				Team t = this.teamService.findTeamById(teamId);
				this.pilotService.savePilot(p, t);
				
			} catch (DataIntegrityViolationException ex) {

				String s = ex.getMessage();
				s = s.substring(s.indexOf("(") + 1);
				s = s.substring(0, s.indexOf(")"));
				s =  s.toLowerCase();				
				result.rejectValue(s, "duplicate", "already exists");
				return "pilots/create";
			}
			
			return "redirect:/welcome";
		}
	}

}

