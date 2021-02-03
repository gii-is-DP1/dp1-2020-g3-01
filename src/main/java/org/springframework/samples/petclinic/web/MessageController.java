package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.Thread;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.ThreadService;
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
	private final ThreadService threadService;

	private static final String VIEWS_MESSAGES_CREATE_OR_UPDATE_FORM = "/messages/createOrUpdateMessageForm";

	@Autowired
	public MessageController(ThreadService threadService, MessageService messageService, UserService userService,
			ManagerService managerService, TeamService teamService) {
		this.messageService = messageService;
		this.userService = userService;
		this.managerService = managerService;
		this.teamService = teamService;
		this.threadService = threadService;

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

	@GetMapping("/teams/forum/thread/{threadId}/messages/{messageId}/details")
	public String showMessage(@PathVariable("threadId") int threadId, @PathVariable("messageId") int messageId,
			ModelMap model) {
		Message message = this.messageService.findMessageById(messageId);
		Thread thread = this.threadService.findThreadById(threadId);
		model.put("message", message);
		model.put("thread", thread);
		return "messages/messageDetails";
	}

	// Nuevo mensaje
	@GetMapping(value = "/teams/forum/thread/{threadId}/message/new")
	public String initCreationForm(@PathVariable("threadId") int threadId, ModelMap model) {
		String s = "";
		// Primero saco quien es el usuario registrado en la aplicacion
		Pilot registeredPilot = this.userService.findPilot();
		Mechanic registeredMechanic = this.userService.findMechanic();
		Manager registeredManager = this.managerService.findOwnerByUserName();

		Collection<Team> cm = this.teamService.findAllTeams();

		if (registeredPilot != null) {
			for (Team t : cm) {
				Set<Pilot> sp = t.getPilot();
				if (sp.contains(registeredPilot)) {
					Message message = new Message();
//					Attachment attachment = new Attachment();
//					message.setAttachment(attachment);
//					AttachmentType[] typesArray = AttachmentType.values();
//					List<AttachmentType> types = Arrays.asList(typesArray);
//					model.put("types", types);

					model.put("message", message);
					s = VIEWS_MESSAGES_CREATE_OR_UPDATE_FORM;
				} else {
					String message = "No seas malo, no puedes crear mensajes en el foro de otros";
					model.put("customMessage", message);
					s = "exception";
				}
			}
		} else if (registeredManager != null) {
			for (Team t : cm) {
				if (t.getManager() == registeredManager) {
					Message message = new Message();
//						Attachment attachment = new Attachment();
//						message.setAttachment(attachment);
//						AttachmentType[] typesArray = AttachmentType.values();
//						List<AttachmentType> types = Arrays.asList(typesArray);
//						model.put("types", types);

					model.put("message", message);
					s = VIEWS_MESSAGES_CREATE_OR_UPDATE_FORM;
				} else {
					String message = "No seas malo, no puedes crear mensajes en el foro de otros";
					model.put("customMessage", message);
					s = "exception";
				}
			}
		} else if (registeredMechanic != null) {
			for (Team t : cm) {
				Set<Mechanic> sm = t.getMechanic();
				if (sm.contains(registeredMechanic)) {
					Message message = new Message();
//							Attachment attachment = new Attachment();
//							message.setAttachment(attachment);
//							AttachmentType[] typesArray = AttachmentType.values();
//							List<AttachmentType> types = Arrays.asList(typesArray);
//							model.put("types", types);

					model.put("message", message);

					s = VIEWS_MESSAGES_CREATE_OR_UPDATE_FORM;
				} else {
					String message = "No seas malo, no puedes crear mensajes en el foro de otros";
					model.put("customMessage", message);
					s = "exception";
				}
			}
		}
		return s;
	}

	@PostMapping(value = "/teams/forum/thread/{threadId}/message/new")
	public String processCreationForm(@Valid Message message, BindingResult result,
			@PathVariable("threadId") int threadId, ModelMap model) {
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

			Thread tr = threadService.findThreadById(threadId);
			List<Message> lm = tr.getMessages();
			lm.add(message);
			this.threadService.saveThread(tr);

			return "redirect:/teams/forum/thread/{threadId}/viewThread";
		}
	}

	// Editar mensaje
	@GetMapping(value = "/teams/forum/thread/messages/{messageId}/edit")
	public String processCreationForm(@PathVariable("messageId") int messageId, ModelMap model) {
		// Primero saco quien es el usuario registrado en la aplicacion
		String s = "";
		Pilot registeredPilot = this.userService.findPilot();
		Mechanic registeredMechanic = this.userService.findMechanic();
		Manager registeredManager = this.managerService.findOwnerByUserName();

		Collection<Team> cm = this.teamService.findAllTeams();

		if (registeredPilot != null) {
			for (Team t : cm) {
				Set<Pilot> sp = t.getPilot();
				if (sp.contains(registeredPilot)) {
					Message message = this.messageService.findMessageById(messageId);
					model.put("message", message);
					s = VIEWS_MESSAGES_CREATE_OR_UPDATE_FORM;
				}else {
					String message = "No seas malo, no puedes editar mensajes en el foro de otros";
					model.put("customMessage", message);
					s = "exception";
				}
			}
		} else if (registeredManager != null) {
			for (Team t : cm) {
				if (t.getManager() == registeredManager) {
					Message message = this.messageService.findMessageById(messageId);
					model.put("message", message);
					s = VIEWS_MESSAGES_CREATE_OR_UPDATE_FORM;
				}else {
					String message = "No seas malo, no puedes editar mensajes en el foro de otros";
					model.put("customMessage", message);
					s = "exception";
				}
			}
		} else if (registeredMechanic != null) {
			for (Team t : cm) {
				Set<Mechanic> sm = t.getMechanic();
				if (sm.contains(registeredMechanic)) {
					Message message = this.messageService.findMessageById(messageId);
					model.put("message", message);
					s = VIEWS_MESSAGES_CREATE_OR_UPDATE_FORM;
				}else {
					String message = "No seas malo, no puedes editar mensajes en el foro de otros";
					model.put("customMessage", message);
					s = "exception";
				}
			}
		}

		return s;
	}

	@PostMapping(value = "/teams/forum/thread/messages/{messageId}/edit")
	public String processUpdateForm(@Valid Message message, BindingResult result,
			@PathVariable("messageId") int messageId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("message", message);
			return VIEWS_MESSAGES_CREATE_OR_UPDATE_FORM;
		} else {
			message.setId(messageId);
			// message.setText(message.getText());
			Date date = new Date();
			message.setCreationDate(date);
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			String username = userDetails.getUsername();
			Optional<User> user = this.userService.findUser(username);
			message.setUser(user.get());
			this.messageService.saveMessage(message);

//			Thread tr = threadService.findThreadById(threadId);
//			List<Message> lm = tr.getMessages();
//			lm.add(message);
//			this.threadService.saveThread(tr);

			return "redirect:/welcome";
		}
	}

	// Delete
	@GetMapping(value = "/teams/forum/thread/{threadId}/messages/{messageId}/delete")
	public String processDeleteForm(@PathVariable("threadId") int threadId, @PathVariable("messageId") int messageId,
			ModelMap model) {

		Thread tr = this.threadService.findThreadById(threadId);

		List<Message> ms = tr.getMessages();
		Message m = this.messageService.findMessageById(messageId);
		int i = ms.indexOf(m);
		ms.remove(i);
		tr.setMessages(ms);
		this.threadService.saveThread(tr);
		this.messageService.removeMessage(messageId);

		return "redirect:/welcome";
	}
}
