package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Forum;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Motorcycle;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.ForumService;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.MotorcycleService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserService;
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
	private final UserService userService;
	
	@Autowired
	public ForumController(ForumService forumService, TeamService teamService, ManagerService managerService, UserService userService) {
		this.forumService = forumService;
		this.teamService = teamService;
		this.managerService = managerService;
		this.userService = userService;
	}
	
	
	
	
	@GetMapping(value = "/teams/forum/newForum")
	public String initCreationForm(ModelMap model) {
		Manager managerRegistered = this.managerService.findOwnerByUserName();
		if (managerRegistered == null) {

			String message = "El foro del equipo no está creado aún, notifica a tu manager para que lo cree";
			model.put("customMessage", message);
			return "exception";
		}
		Forum forum = new Forum();
		model.put("forum", forum);
		
		return "forum/createOrUpdateForum";
	}
	
	@PostMapping(value = "/teams/forum/newForum")
	public String processCreationForm(@Valid Forum forum, 
			BindingResult result, ModelMap model) 
			throws DataAccessException {
		if (result.hasErrors()) {
			model.put("forum", forum);
			return "forum/createOrUpdateForum";
		} else {
			Manager managerRegistered = this.managerService.findOwnerByUserName();
			Collection<Team> ct = this.teamService.findAllTeams();
			Team team = new Team();
			for(Team t : ct) {
				if(t.getManager() == managerRegistered) {
					team = t;
				}
			}
			Integer teamId = team.getId();
			forum.setTeam(this.teamService.findTeamById(teamId));
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
	
	@GetMapping("/teams/forum/showForum")
		public String showForum(ModelMap model) {
		Pilot p = this.userService.findPilot();
		Manager m = this.managerService.findOwnerByUserName();
		Mechanic mc = this.userService.findMechanic();
		Collection<Team> cm = this.teamService.findAllTeams();
		int teamId = 0;
		if(p != null) {
			for(Team t : cm) {
				Set<Pilot> sp = t.getPilot();
				if(sp.contains(p)) {
					
					teamId = t.getId();
				}
			}
		}else {
			if(m != null) {
				for(Team t : cm) {
					if(t.getManager() == m) {
						teamId = t.getId();
					}
				}
			} else {
				if(mc != null) {
					for(Team t : cm) {
						Set<Mechanic> sm = t.getMechanic();
						if(sm.contains(mc)) {
							teamId = t.getId();
						}
					}
				}
			}
			
		}
			
			Forum f = this.forumService.findForumByTeamId(teamId);
			Boolean hasForum = true;
			if(f == null) {
				hasForum = false;
			}
			model.put("teamId", teamId);
			model.put("forum", f);
			model.put("hasForum", hasForum);
			
			return "forum/showForum";
		}
	
	@GetMapping("/teams/forum/{forumId}/deleteForum")
	public String deleteForum(@PathVariable("forumId") int forumId,ModelMap model) {

		
			this.forumService.removeForum(forumId);

			return "redirect:/welcome";
		
	}
	@GetMapping(value = "/teams/forum/{forumId}/editForum")
	public String initUpdateForm( @PathVariable("forumId") int forumId, ModelMap model) {
		Manager managerRegistered = this.managerService.findOwnerByUserName();
		if (managerRegistered == null) {

			String message = "El foro del equipo no puede ser editado si no eres el manager"
					+ ", notifica a tu manager para que lo haga";
			model.put("customMessage", message);
			return "exception";
		}
		Forum forum = this.forumService.findForumById(forumId);
		model.put("forum", forum);
		return "forum/createOrUpdateForum";
	}

	@PostMapping(value = "/teams/forum/{forumId}/editForum")
	public String processUpdateForm(@PathVariable("forumId") int forumId, @Valid Forum f, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.put("forum", f);
			return "forum/createOrUpdateForum";
		} else {
			Manager managerRegistered = this.managerService.findOwnerByUserName();
			Collection<Team> ct = this.teamService.findAllTeams();
			Team team = new Team();
			for(Team t : ct) {
				if(t.getManager() == managerRegistered) {
					team = t;
				}
			}
			Integer teamId = team.getId();
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
	


