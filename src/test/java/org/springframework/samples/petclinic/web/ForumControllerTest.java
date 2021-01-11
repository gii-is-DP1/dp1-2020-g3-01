package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Forum;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Thread;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ForumService;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.ThreadService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = MechanicController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
public class ForumControllerTest {

	@MockBean
	private ForumService forumService;
	@MockBean
	private AuthoritiesService authoritiesService;
	@MockBean
	private TeamService teamService;
	@MockBean
	private ThreadService threadService;
	@MockBean
	private ManagerService managerService;
	@MockBean
	private PilotService pilotService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private static final int TEST_FORUM_ID = 24;
	private static final int TEST_TEAM_ID = 3;
	private static final int TEST_MANAGER_ID = 6;
	private static final int TEST_PILOT_ID = 22;
	
	@BeforeEach
	void setup() {
		
		//User1
		User user1 = new User();
		user1.setUsername("rafavisan");
		user1.setPassword("rafavisan");
		user1.setEnabled(true);
		
		//User2
		User user2 = new User();
		user2.setUsername("kimlinket");
		user2.setPassword("kimlinket");
		user2.setEnabled(true);
		
		//Manager
		Manager manager = new Manager();
		manager.setBirthDate(LocalDate.of(1999, 8, 24));
		manager.setDni("12345678Z");
		manager.setFirstName("Rafa");
		manager.setId(6);
		manager.setLastName("Avila");
		manager.setNationality("Spain");
		manager.setResidence("Sevilla");
		manager.setUser(user1);
		
		//Pilot
		Pilot pilot = new Pilot();
		pilot.setBirthDate(LocalDate.of(2001, 6, 10));
		pilot.setDni("87654321J");
		pilot.setFirstName("Kim");
		pilot.setHeight(1.7);
		pilot.setId(22);
		pilot.setLastName("Ketris");
		pilot.setNationality("Latvia");
		pilot.setNumber(22);
		pilot.setResidence("Sevilla");
		pilot.setUser(user2);
		pilot.setWeight(55.);
		
		Set<Pilot> setP = new HashSet<>();
		setP.add(pilot);
		
		//Team
		Team team = new Team();
		team.setCreationDate(Date.from(Instant.now()));
		team.setManager(manager);
		team.setName("DELLAFUENTE M.C.");
		team.setNif("13181318D");
		team.setPilot(setP);
		
		//Forum
		Forum forum = new Forum();
		forum.setCreationDate(Date.from(Instant.now()));
		forum.setId(24);
		forum.setName("Test");
		forum.setTeam(team);
		List<Thread> threads = new ArrayList<Thread>();
		forum.setThreads(threads);
		
		given(this.forumService.findForumById(TEST_FORUM_ID)).willReturn(forum);
		
		given(this.managerService.findManagerById(TEST_MANAGER_ID)).willReturn(manager);
		
		given(this.pilotService.findById(TEST_PILOT_ID)).willReturn(pilot);
		
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(team);
	}
	
	//Forum Details
	@WithMockUser(value = "rafavisan", authorities = "manager")
	@Test
	void testShowForum() throws Exception {
		mockMvc.perform(get("/managers/{managerId}/teams/{teamId}/forum/showForum", TEST_MANAGER_ID, TEST_TEAM_ID))
		  .andExpect(status().isOk())
		  .andExpect(model().attributeExists("forum"))
		  .andExpect(view().name("forum/showForum"));
	}
	
	//Insert new Forum
	@WithMockUser(value = "rafavisan", authorities = "manager")
	@Test
	void testGetNewForum() throws Exception {
		mockMvc.perform(get("/managers/{managerId}/teams/{teamId}/forum/newForum", TEST_MANAGER_ID, TEST_TEAM_ID))
		  .andExpect(status().isOk())
		  .andExpect(model().attributeExists("forum"))
		  .andExpect(view().name("forum/createOrUpdateForum"));
	}
	
	@WithMockUser(value = "rafavisan", authorities = "manager")
	@Test
	void testCreateNewForumFormSuccess() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/forum/newForum", TEST_MANAGER_ID, TEST_TEAM_ID))
		  .with(csrf())
		  .param("id", "24")
		  .param("name", "Test")
		  .param("")
	}
	
}
