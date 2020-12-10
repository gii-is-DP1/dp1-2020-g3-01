package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.Type;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.MechanicService;
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserService;
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
public class MessageController {

	private final MessageService messageService;
	private final UserService userService;
	private final ManagerService managerService;
	private final TeamService teamService;
	
	private static final String VIEWS_MESSAGES_CREATE_OR_UPDATE_FORM = "messages/createOrUpdateMessageForm";

	@Autowired
	public MessageController(MessageService messageService, UserService userService, ManagerService managerService,
			TeamService teamService) {
		this.messageService = messageService;
		this.userService = userService;
		this.managerService = managerService;
		this.teamService = teamService;

	}
	
//	@InitBinder
//	public void setAllowedFields(WebDataBinder dataBinder) {
//		dataBinder.setDisallowedFields("id");
//	}
//
//	@InitBinder("mechanic")
//	public void initPetBinder(WebDataBinder dataBinder) {
//		dataBinder.setValidator(new MechanicValidator());
//	}
	
	// Show mensaje
	
	@GetMapping("managers/{managerId}/teams/{teamId}/forum/{messageId}/details")
	public String showTeam(@PathVariable("managerId") int managerId, @PathVariable("teamId") int teamId, 
			@PathVariable("messageId") int messageId,ModelMap model) {
		Message message = this.messageService.findMessageById(messageId);
		model.put("message", message);
		return "message/messageDetails";
	}
	
	


	@GetMapping(value = "/team/{teamId}/forum/messages/new")
	public String initCreationForm(@PathVariable("teamId") int teamId, @PathVariable("username") String username, ModelMap model) {
		
		// Primero saco quien es el usuario registrado en la aplicacion
		Pilot registeredPilot = this.userService.findPilot();
		Mechanic registeredMechanic = this.userService.findMechanic();
		Manager registeredManager = this.managerService.findOwnerByUserName();
		
		// Una vez que sé quien es el registrado, tengo que ver a que equipo pertenece
		
		if(registeredPilot != null) {
			int pilotId = registeredPilot.getId();
			
			// Hay que llamar a algun metodo que me saque el id del team con el id del piloto
			
			Team team = this.teamService.findTeamByPilotId(pilotId);
			int team_id = team.getId();
			
			// Si el equipo que pasamos por parametros en la url no coincide con el equipo
			// al que pertenece el piloto registrado, se devuelve un mensaje de error
			if(teamId != team_id) {
				String message = "No seas malo, no puedes escribir mensajes en el foro de otro equipo.";
				model.put("customMessage", message);
				return "exception";
			} else{
				Message message = new Message();
				model.put("message", message);
				return VIEWS_MESSAGES_CREATE_OR_UPDATE_FORM;
			}
			
		} else if(registeredMechanic != null) {
			int mechanicId = registeredMechanic.getId();
			
			// Hay que llamar a algun metodo que me saque el id del team con el id del mecanico
			
			Team team = this.teamService.findTeamByMechanicId(mechanicId);
			int team_id = team.getId();
			
			// Si el equipo que pasamos por parametros en la url no coincide con el equipo
			// al que pertenece el mecanico registrado, se devuelve un mensaje de error
			if(teamId != team_id) {
				String message = "No seas malo, no puedes escribir mensajes en el foro de otro equipo.";
				model.put("customMessage", message);
				return "exception";
			} else{
				Message message = new Message();
				model.put("message", message);
				return VIEWS_MESSAGES_CREATE_OR_UPDATE_FORM;
			}
			
		}
		return null;
		
	}
	

//	@PostMapping(value = "managers/{managerId}/teams/new")
//	public String processCreationForm( @Valid Team team, @PathVariable("managerId") int managerId, BindingResult result, ModelMap model) {
//		if (result.hasErrors()) {
//			model.put("team", team);
//			return "teams/createOrUpdateTeamForm";
//		} else {
//
//			team.setManager(this.managerService.findManagerById(managerId));
//			Date creation = new Date();
//			team.setCreationDate(creation);
//			this.teamService.saveTeam(team);
//			System.out.println(team);
//			return "redirect:/welcome";
//		}
//	}
	
//  	@GetMapping(value = "/managers/{managerId}/teams/{teamId}/remove")
//	public String processDeleteForm(@PathVariable("managerId") int managerId, ModelMap model) {
//		Manager managerRegistered = this.managerService.findOwnerByUserName();
//		if (managerRegistered.getId() != managerId) {
//
//			String message = "No seas malo, no puedes eliminar equipos por otro";
//			model.put("customMessage", message);
//			return "exception";
//		} else {
//			Team team = this.teamService.findManager(managerId);
//			this.teamService.removeTeam(team.getId());
//
//			return "redirect:/managers/details";
//		}
//	}
  
//	@PostMapping(value = "/managers/{managerId}/teams/{teamId}/edit")
//	public String processUpdateForm(@Valid Team team, BindingResult result, @PathVariable("managerId") int managerId, 
//			ModelMap model) {
//		if (result.hasErrors()) {
//			model.put("team", team);
//			return VIEWS_TEAMS_CREATE_OR_UPDATE_FORM;
//		} else {
//			Team teamid = this.teamService.findManager(managerId);
//			Manager manager = this.managerService.findManagerById(managerId);
//			
//			Date fecha = new Date();
//			team.setId(teamid.getId());
//			team.setCreationDate(fecha);
//			Set<Mechanic> mechanics = this.teamService.getMechanicsById(team.getId());
//			team.setManager(manager);
//			team.setMechanic(mechanics);
//
//			this.teamService.saveTeam(team);
//			return "redirect:/managers/"+ managerId +"/teams/" + team.getId() + "/details";
//		}
//	}
}
