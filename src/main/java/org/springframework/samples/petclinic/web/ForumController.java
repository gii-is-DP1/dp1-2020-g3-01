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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.samples.petclinic.model.Thread;
@Controller
public class ForumController {
	
	private final ForumService forumService;
	private final TeamService teamService;
	private final ManagerService managerService;
	
	@Autowired
	public ForumController(ForumService forumService, TeamService teamService, ManagerService managerService) {
		this.forumService = forumService;
		this.teamService = teamService;
		this.managerService = managerService;
	}
	
	@ModelAttribute("team")
    public Team findTeam(@PathVariable("teamId") int teamId) {
        return this.teamService.findTeamById(teamId);
    }
	
	@ModelAttribute("manager")
    public Manager findById(@PathVariable("managerId") int managerId) {
        return this.managerService.findManagerById(managerId);
    }
	
	@GetMapping(value = "/managers/{managerId}/teams/{teamId}/forum/newForum")
	public String initCreationForm(@PathVariable("teamId") int teamId, ModelMap model) {
		Manager managerRegistered = this.managerService.findOwnerByUserName();
		Manager teamManager = this.teamService.findTeamById(teamId).getManager();
		if(managerRegistered.getId()!=teamManager.getId()) {
			String message = "No seas malo, no puedes crear foros por otro";
			model.put("customMessage", message);
			return "exception";
		}
		
		Forum forum = new Forum();
		model.put("forum", forum);
		
		return "forum/createOrUpdateForum";
	}
	
	@PostMapping(value = "/managers/{managerId}/teams/{teamId}/forum/newForum")
	public String processCreationForm(@PathVariable("teamId")int teamId,@PathVariable("managerId") int managerId, @Valid Forum forum, 
			BindingResult result, ModelMap model) 
			throws DataAccessException {
		if (result.hasErrors()) {
			model.put("forum", forum);
			return "forum/createOrUpdateForum";
		} else {
			Team t = this.teamService.findTeamById(teamId);
			forum.setTeam(t);
			Date creation = new Date();
			forum.setCreationDate(creation);
			List<Thread> lt = new ArrayList<>();
			Thread tr = new Thread();
			tr.setTitle("Welcome thread");
			tr.setCreationDate(creation);
			List<Message> lm = new ArrayList<>();
			Message m = new Message();
			m.setCreationDate(creation);
			m.setTitle("Welcome Message");
			m.setText("This is a thread to introduce yourselves to the others members of the team");
			m.setUser(this.managerService.findOwnerByUserName().getUser());
			lm.add(m);
			tr.setMessages(lm);
			lt.add(tr);
			forum.setThreads(lt);
			this.forumService.saveForum(forum);
	
			return "redirect:/welcome";
		}
	}
	
	@GetMapping("/managers/{managerId}/teams/{teamId}/forum/showForum")
		public String showForum(@PathVariable("teamId") int teamId, ModelMap model) {
			Forum f = this.forumService.findForumByTeamId(teamId);
	
			model.put("forum", f);
			
			return "forum/showForum";
		}
	@GetMapping("managers/{managerId}/teams/{teamId}/forum/{forumId}/deleteForum")
	public String deleteForum(@PathVariable("forumId") int forumId,@PathVariable("managerId") int managerId,ModelMap model) {
		Manager managerRegistered = this.managerService.findOwnerByUserName();
		if (managerRegistered.getId() != managerId) {

			String message = "No seas malo, no puedes eliminar foros por otro";
			model.put("customMessage", message);
			return "exception";
		} else {
			this.forumService.removeForum(forumId);

			return "redirect:/welcome";
		}
	}
	@GetMapping(value = "/managers/{managerId}/teams/{teamId}/forum/{forumId}/editForum")
	public String initUpdateForm(@PathVariable("managerId") int managerId, @PathVariable("forumId") int forumId, ModelMap model) {
		Manager managerRegistered = this.managerService.findOwnerByUserName();
		if (managerRegistered.getId() != managerId) {

			String message = "No seas malo, no puedes editar foros por otro";
			model.put("customMessage", message);
			return "exception";
		}

		Forum forum = this.forumService.findForumById(forumId);
		model.put("forum", forum);
		return "forum/createOrUpdateForum";
	}

	@PostMapping(value = "/managers/{managerId}/teams/{teamId}/forum/{forumId}/editForum")
	public String processUpdateForm(@PathVariable("managerId") int managerId, @PathVariable("forumId") int forumId,@PathVariable
			("teamId") int teamId, @Valid Forum f, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("forum", f);
			return "forum/createOrUpdateForum";
		} else {
			Forum foro = forumService.findForumById(forumId);
			Date d = new Date();
			f.setCreationDate(d);
			f.setId(forumId);
			f.setTeam(teamService.findTeamById(teamId));
			f.setThreads(foro.getThreads());
			this.forumService.saveForum(f);
			return "redirect:/welcome";
		}
		
	}	
	}
	


