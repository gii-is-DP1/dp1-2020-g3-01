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
import java.util.Collection;
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
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Thread;
import org.springframework.samples.petclinic.model.Type;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ForumService;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.MechanicService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.ThreadService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ForumController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
public class ForumControllerTests {

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
	@MockBean
	private MechanicService mechanicService;
	@MockBean
	private UserService userService;
	@Autowired
	private MockMvc mockMvc;

	private static final int TEST_FORUM_ID = 24;
	private static final int TEST_TEAM_ID = 3;
	private static final int TEST_MANAGER_ID = 6;

	@BeforeEach
	void setup() {

		//User1
		User user1 = new User();
		user1.setUsername("rafavisan");
		user1.setPassword("rafavisan");
		user1.setEnabled(true);

		//Manager
		Manager manager1 = new Manager();
		manager1.setBirthDate(LocalDate.of(1999, 8, 24));
		manager1.setDni("12345678Z");
		manager1.setFirstName("Rafa");
		manager1.setId(TEST_MANAGER_ID);
		manager1.setLastName("Avila");
		manager1.setNationality("Spain");
		manager1.setResidence("Sevilla");
		manager1.setUser(user1);

		//Team
		Team team = new Team();
		team.setCreationDate(Date.from(Instant.now()));
		team.setManager(manager1);
		team.setName("DELLAFUENTE M.C.");
		team.setNif("13181318D");
		team.setId(TEST_TEAM_ID);
		//Forum
		List<Thread> threads = new ArrayList<Thread>();
		Forum forum = new Forum();
		forum.setCreationDate(Date.from(Instant.now()));
		forum.setId(24);
		forum.setName("Test");
		forum.setTeam(team);
		forum.setThreads(threads);
		forum.setId(TEST_FORUM_ID);
		//TeamCollection
		Collection<Team> teams = new ArrayList<Team>();
		teams.add(team);
		given(this.forumService.findForumByTeamId(TEST_TEAM_ID)).willReturn(forum);

		given(this.managerService.findManagerById(TEST_MANAGER_ID)).willReturn(manager1);

		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(team);
		
		given(this.forumService.findForumById(TEST_FORUM_ID)).willReturn(forum);
		
		given(this.managerService.findOwnerByUserName()).willReturn(manager1);
		
		given(this.teamService.findAllTeams()).willReturn(teams);
	}

	
	//Insert new Forum
	
	@WithMockUser(value = "manager1", authorities = {"manager"})
	@Test
	void testGetNewForum() throws Exception {
		mockMvc.perform(get("/teams/forum/newForum"))
		  .andExpect(status().isOk())
		  .andExpect(view().name("forum/createOrUpdateForum"))
		  .andExpect(model().attributeExists("forum"));
	}

	@WithMockUser(value = "manager1", authorities = {"manager"})
	@Test
	void testCreateNewForumFormSuccess() throws Exception {
		
		mockMvc.perform(post("/teams/forum/newForum")
		.with(csrf())
		.param("name", "Test"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/welcome"));
	}
	
	@WithMockUser(value = "manager1", authorities = {"manager"})
	@Test
	void testCreateForumFormHasErrors() throws Exception {
		mockMvc.perform(post("/teams/forum/newForum")
				.with(csrf())
				.param("name", "H"))
				.andExpect(model().attributeHasErrors("forum"))
				.andExpect(model().attributeHasFieldErrors("forum", "name"))
				.andExpect(status().isOk())
				.andExpect(view().name("forum/createOrUpdateForum"));
	}
	
//Forum Details
	
	@WithMockUser(value = "manager1", authorities = {"manager"})
	@Test
	void testShowForum() throws Exception {
		mockMvc.perform(get("/teams/forum/showForum"))
	    .andExpect(status().isOk())
		.andExpect(view().name("forum/showForum")).andExpect(model().attributeExists("forum"));
		}
	
//	Delete Forum
	
	@WithMockUser(value = "manager1", authorities = {"manager"})
	@Test
	void testDeleteForum() throws Exception {
		mockMvc.perform(get("/teams/forum/{forumId}/deleteForum",TEST_FORUM_ID))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/welcome"));
		}
	
//	 Edit forum
	
	@WithMockUser(value = "manager1", authorities = {"manager"})
	@Test
	void testEditForum() throws Exception {
		mockMvc.perform(get("/teams/forum/{forumId}/editForum",TEST_FORUM_ID))
		  .andExpect(status().isOk())
		  .andExpect(view().name("forum/createOrUpdateForum"))
		  .andExpect(model().attributeExists("forum"));
	}

	@WithMockUser(value = "manager1", authorities = {"manager"})
	@Test
	void testEditForumFormSuccess() throws Exception {
		mockMvc.perform(post("/teams/forum/{forumId}/editForum", TEST_FORUM_ID)
		.with(csrf())
		.param("name", "Test"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/welcome"));
	}
	@WithMockUser(value = "manager1", authorities = {"manager"})
	@Test
	void testEditForumFormHasErrors() throws Exception {
		mockMvc.perform(post("/teams/forum/{forumId}/editForum", TEST_FORUM_ID)
		.with(csrf())
		.param("name", "f")).andExpect(model().attributeHasErrors("forum"))
		.andExpect(model().attributeHasFieldErrors("forum", "name"))
		.andExpect(view().name("forum/createOrUpdateForum"));
	}
}
