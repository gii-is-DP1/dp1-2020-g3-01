package org.springframework.samples.petclinic.web;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Forum;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Motorcycle;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.ForumService;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.MotorcycleService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.ThreadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.samples.petclinic.model.Thread;
@Controller
public class ThreadController {
	private final ForumService forumService;
	private final TeamService teamService;
	private final ManagerService managerService;
	private final ThreadService threadService;
	
	@Autowired
	public ThreadController(ForumService forumService, TeamService teamService, ManagerService managerService, ThreadService threadService) {
		this.forumService = forumService;
		this.teamService = teamService;
		this.managerService = managerService;
		this.threadService = threadService;
	}
	
	@ModelAttribute("team")
    public Team findTeam(@PathVariable("teamId") int teamId) {
        return this.teamService.findTeamById(teamId);
    }
	
	@ModelAttribute("manager")
    public Manager findById(@PathVariable("managerId") int managerId) {
        return this.managerService.findManagerById(managerId);
    }
	
	@GetMapping("/managers/{managerId}/teams/{teamId}/forum/thread/{threadId}/viewThread")
	public String showThread(@PathVariable("threadId") int threadId, ModelMap model) {
		Thread t = this.threadService.findThreadById(threadId);

		model.put("thread", t);
		
		return "threads/threadView";
	}
	
	@GetMapping(value = "/managers/{managerId}/teams/{teamId}/forum/{forumId}/thread/newThread")
	public String initCreationForm(ModelMap model) {
		
		Thread t = new Thread();
		model.put("thread", t);
		
		return "threads/createOrUpdateThread";
	}
	
	@PostMapping(value = "/managers/{managerId}/teams/{teamId}/forum/{forumId}/thread/newThread")
	public String processCreationForm(@PathVariable("forumId") int forumId, @Valid Thread thread, 
			BindingResult result, ModelMap model) 
			throws DataAccessException {
		if (result.hasErrors()) {
			model.put("thread", thread);
			return "forum/createOrUpdateForum";
		} else {
			Forum f = this.forumService.findForumById(forumId);
			Date creation = new Date();
			thread.setCreationDate(creation);
			thread.setCreationDate(creation);
			List<Message> lm = new ArrayList<>();
			thread.setMessages(lm);
			List<Thread> lt = f.getThreads();
			lt.add(thread);
			f.setThreads(lt);
			this.threadService.saveThread(thread);
	
			return "threads/threadView";
		}
	}
	@GetMapping("managers/{managerId}/teams/{teamId}/forum/{forumId}/{threadId}/deleteThread")
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

