package org.springframework.samples.petclinic.web;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Attachment;
import org.springframework.samples.petclinic.model.AttachmentType;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Motorcycle;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.MessageService;
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
			@PathVariable("messageId") int messageId, ModelMap model) {
		Message message = this.messageService.findMessageById(messageId);
		model.put("message", message);
		return "message/messageDetails";
	}

	//Nuevo mensaje
	@GetMapping(value = "team/{teamId}/forum/messages/new")
	public String initCreationForm(@PathVariable("teamId") int teamId, ModelMap model) {

		// System.out.println("Estamos dentro de la ruta");

		// Primero saco quien es el usuario registrado en la aplicacion
		Pilot registeredPilot = this.userService.findPilot();
		Mechanic registeredMechanic = this.userService.findMechanic();
		Manager registeredManager = this.managerService.findOwnerByUserName();

		Team team = teamService.findTeamById(teamId);
//		
//		System.out.println("Hay un piloto registrado?" + registeredPilot);
//		System.out.println("Hay un mecanico registrado?" + registeredMechanic);
//		System.out.println("Hay un manager registrado?" + registeredManager);

		// Una vez que sé quien es el registrado, tengo que ver a que equipo pertenece
		if (registeredPilot != null) {

			Set<Pilot> pilot = team.getPilot();

			if (!(pilot.contains(registeredPilot))) {
				String message = "No seas malo, no puedes escribir mensajes en el foro de otro equipo.";
				model.put("customMessage", message);
				return "exception";
			} else {
				Message message = new Message();
//				Attachment attachment = new Attachment();
//				message.setAttachment(attachment);
//				AttachmentType[] typesArray = AttachmentType.values();
//				List<AttachmentType> types = Arrays.asList(typesArray);
//				model.put("types", types);

				model.put("message", message);
				return VIEWS_MESSAGES_CREATE_OR_UPDATE_FORM;
			}

		} else if (registeredMechanic != null) {
			Set<Mechanic> mechanic = team.getMechanic();
			System.out.println("Estoy aqui");

			// Si el equipo que pasamos por parametros en la url no coincide con el equipo
			// al que pertenece el mecanico registrado, se devuelve un mensaje de error
			if (!(mechanic.contains(registeredMechanic))) {
				String message = "No seas malo, no puedes escribir mensajes en el foro de otro equipo.";
				model.put("customMessage", message);
				return "exception";
			} else {
				Message message = new Message();
//				Attachment attachment = new Attachment();
//				message.setAttachment(attachment);
//				AttachmentType[] typesArray = AttachmentType.values();
//				List<AttachmentType> types = Arrays.asList(typesArray);
//				model.put("types", types);

				model.put("message", message);
				return VIEWS_MESSAGES_CREATE_OR_UPDATE_FORM;
			}

		} else if (registeredManager != null) {
			Integer managerId = registeredManager.getId();
			Team teamManager = teamService.findManager(managerId);
			if (teamManager.getId() != teamId) {
				String message = "No seas malo, no puedes escribir mensajes en el foro de otro equipo.";
				model.put("customMessage", message);
				return "exception";
			} else {
				Message message = new Message();
//				Attachment attachment = new Attachment();
//				message.setAttachment(attachment);
//				AttachmentType[] typesArray = AttachmentType.values();
//				List<AttachmentType> types = Arrays.asList(typesArray);
//				model.put("types", types);

				model.put("message", message);
				return VIEWS_MESSAGES_CREATE_OR_UPDATE_FORM;
			}

		} else {
			return "redirect:/welcome";
		}

	}

	@PostMapping(value = "team/{teamId}/forum/messages/new")
	public String processCreationForm(@Valid Message message, BindingResult result, @PathVariable("teamId") int teamId,
			ModelMap model) {
		if (result.hasErrors()) {
			model.put("message", message);
			return VIEWS_MESSAGES_CREATE_OR_UPDATE_FORM;
		} else {
			Date date = new Date();
			message.setCreationDate(date);
			// Se saca el usuario registrado
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			String username = userDetails.getUsername();
			Optional<User> user = this.userService.findUser(username);
			// Y se relaciona el usuario al mensaje
			message.setUser(user.get());
			this.messageService.saveMessage(message);
			return "redirect:/welcome";
		}
	}

	
	
	// Editar mensaje
	@GetMapping(value = "team/{teamId}/forum/messages/{messageId}/edit")
	public String processCreationForm(@PathVariable("teamId") int teamId, @PathVariable("messageId") int messageId,
			ModelMap model) {
		Pilot registeredPilot = this.userService.findPilot();
		Mechanic registeredMechanic = this.userService.findMechanic();
		Manager registeredManager = this.managerService.findOwnerByUserName();
		Team team = teamService.findTeamById(teamId);
		if (registeredPilot != null) {

			Set<Pilot> pilot = team.getPilot();

			if (!(pilot.contains(registeredPilot))) {
				String messageError = "No seas malo, no puedes escribir mensajes en el foro de otro equipo.";
				model.put("customMessage", messageError);
				return "exception";
			} else {
				Message message = messageService.findMessageById(messageId);
				model.put("message", message);
				return VIEWS_MESSAGES_CREATE_OR_UPDATE_FORM;
			}

		} else if (registeredMechanic != null) {
			Set<Mechanic> mechanic = team.getMechanic();
			System.out.println("Estoy aqui");

			if (!(mechanic.contains(registeredMechanic))) {
				String messageError = "No seas malo, no puedes escribir mensajes en el foro de otro equipo.";
				model.put("customMessage", messageError);
				return "exception";
			} else {
				Message message = messageService.findMessageById(messageId);

				model.put("message", message);
				return VIEWS_MESSAGES_CREATE_OR_UPDATE_FORM;
			}

		} else if (registeredManager != null) {
			Integer managerId = registeredManager.getId();
			Team teamManager = teamService.findManager(managerId);
			if (teamManager.getId() != teamId) {
				String messageError = "No seas malo, no puedes escribir mensajes en el foro de otro equipo.";
				model.put("customMessage", messageError);
				return "exception";
			} else {
				Message message = messageService.findMessageById(messageId);

				model.put("message", message);
				return VIEWS_MESSAGES_CREATE_OR_UPDATE_FORM;
			}
		} else {
			return "redirect:/welcome";
		}
	}

	@PostMapping(value = "team/{teamId}/forum/messages/{messageId}/edit")
	public String processUpdateForm(@Valid Message message, BindingResult result, @PathVariable("teamId") int teamId,
			@PathVariable("messageId") int messageId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("message", message);
			return VIEWS_MESSAGES_CREATE_OR_UPDATE_FORM;
		} else {
			message.setId(messageId);
			//message.setText(message.getText());
			Date date = new Date();
			message.setCreationDate(date);
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			String username = userDetails.getUsername();
			Optional<User> user = this.userService.findUser(username);
			message.setUser(user.get());
			System.out.println("Porque no funciona");
			
			this.messageService.saveMessage(message);
			System.out.println(message);
			return "redirect:/managers/details";
		}
	}
	

	//Delete
	@GetMapping(value = "managers/{managerId}/team/{teamId}/forum/messages/{messageId}/remove")
	public String processDeleteForm(@PathVariable("managerId") int managerId, @PathVariable("teamId") int teamId, @PathVariable("messageId") int messageId,
			ModelMap model) {
		Manager managerRegistered = this.managerService.findOwnerByUserName();
		if (managerRegistered.getId() != managerId) {

			String message = "No seas malo, no puedes eliminar mensajes por otro";
			model.put("customMessage", message);
			return "exception";
		} else {
			this.messageService.removeMessage(messageId);

			return "redirect:/managers/details";
		}
	}

}
