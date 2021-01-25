package org.springframework.samples.petclinic.web;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Forum;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.Thread;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.ForumService;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.ThreadService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class ThreadController {
	private final ForumService forumService;
	private final TeamService teamService;
	private final ManagerService managerService;
	private final ThreadService threadService;
	private final UserService userService;
	
	@Autowired
	public ThreadController(UserService userService,ForumService forumService, TeamService teamService, ManagerService managerService, ThreadService threadService) {
		this.forumService = forumService;
		this.teamService = teamService;
		this.managerService = managerService;
		this.threadService = threadService;
		this.userService = userService;
	}
	
	
	@GetMapping("/teams/forum/thread/{threadId}/viewThread")
	public String showThread(@PathVariable("threadId") int threadId, ModelMap model) {
		Thread t = this.threadService.findThreadById(threadId);

		model.put("thread", t);
		
		return "threads/threadView";
	}
	
	@GetMapping(value = "/teams/forum/{forumId}/thread/newThread")
	public String initCreationForm(ModelMap model) {
		
		Thread t = new Thread();
		Pilot registeredPilot = this.userService.findPilot();
		Mechanic registeredMechanic = this.userService.findMechanic();
		Manager registeredManager = this.managerService.findOwnerByUserName();
		Team team = teamService.findTeamById(1);
		if (registeredPilot != null) {

			Set<Pilot> pilot = team.getPilot();

			if (!(pilot.contains(registeredPilot))) {
				String messageError = "No seas malo, no puedes escribir threads en el foro de otro equipo.";
				model.put("customMessage", messageError);
				return "exception";
			} else {
				
				model.put("thread", t);
				
				return "threads/createOrUpdateThread";
			}

		} else if (registeredMechanic != null) {
			Set<Mechanic> mechanic = team.getMechanic();
			if (!(mechanic.contains(registeredMechanic))) {
				String messageError = "No seas malo, no puedes escribir threads en el foro de otro equipo.";
				model.put("customMessage", messageError);
				return "exception";
			} else {
				
				model.put("thread", t);
				
				return "threads/createOrUpdateThread";
			}

		} else if (registeredManager != null) {

			
				
				model.put("thread", t);			
				return "threads/createOrUpdateThread";
			
		} else {
			return "redirect:/welcome";
		}

	}
	
	@PostMapping(value = "/teams/forum/{forumId}/thread/newThread")
	public String processCreationForm(@PathVariable("forumId") int forumId, @Valid Thread thread, 
			BindingResult result, ModelMap model) 
			throws DataAccessException {
		if (result.hasErrors()) {
			model.put("thread", thread);
			return "threads/createOrUpdateThread";
		} else {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			String username = userDetails.getUsername();
			Optional<User> user = this.userService.findUser(username);
			Forum f = this.forumService.findForumById(forumId);
			Date creation = new Date();
			thread.setCreationDate(creation);
			thread.setUser(user.get());
			List<Message> lm = new ArrayList<>();
			thread.setMessages(lm);
			List<Thread> lt = f.getThreads();
			lt.add(thread);
			f.setThreads(lt);
			this.threadService.saveThread(thread);
	
			return "redirect:/teams/forum/showForum";
		}
	}
	@GetMapping("/teams/forum/{forumId}/{threadId}/deleteThread")
	public String deleteThread(@PathVariable("threadId") int threadId, @PathVariable("forumId") int forumId, ModelMap model) {
			
			Forum f = this.forumService.findForumById(forumId);
			Thread t = this.threadService.findThreadById(threadId);
			List<Thread> lt = f.getThreads();
			lt.remove(t);
			f.setThreads(lt);
			this.forumService.saveForum(f);
			this.threadService.removeThread(threadId);

			return "redirect:/welcome";
		}
	
	
	}

