package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Forum;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.ForumService;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	private final UserService userService;
	private final ManagerService managerService;
	private final TeamService teamService;
	private final ForumService forumService;
	
	@Autowired
	public WelcomeController(UserService userService, ManagerService managerService, TeamService teamService, ForumService forumService) {
		
		this.userService = userService;
		this.managerService = managerService;
		this.teamService = teamService;
		this.forumService = forumService;
	}

	@GetMapping(value = "/accessDenied")
	public String accessDenied(ModelMap model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		model.put("auth", auth.getName());

		return "accessDenied";

	}

	@GetMapping({ "/", "/welcome" })
	public String welcome(Map<String, Object> model) {

		List<Person> people = new ArrayList<Person>();
		Person person1 = new Person();
		person1.setFirstName("César");
		person1.setLastName("Gálvez");

		Person person2 = new Person();
		person2.setFirstName("Joaquín");
		person2.setLastName("González");

		Person person3 = new Person();
		person3.setFirstName("Diego Miguel");
		person3.setLastName("Rodríguez");

		Person person4 = new Person();
		person4.setFirstName("Rafael");
		person4.setLastName("Ávila");

		Person person5 = new Person();
		person5.setFirstName("Carlos");
		person5.setLastName("Pardo");

		people.add(person1);
		people.add(person2);
		people.add(person3);
		people.add(person4);
		people.add(person5);
		
		Pilot p = this.userService.findPilot();
		Manager m = this.managerService.findOwnerByUserName();
		Mechanic mc = this.userService.findMechanic();
		Collection<Team> cm = this.teamService.findAllTeams();
		int teamId = 0 ;
		Boolean hasForum = true;
	
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
		if(f == null) {
			hasForum = false;
		}
		model.put("hasForum", hasForum);
		model.put("people", people);
		model.put("title", "ForoMotos");
		model.put("group", "G3-01");

		return "welcome";
	}
}
