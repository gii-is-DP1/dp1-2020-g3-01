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
import java.util.Optional;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ThreadController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)

public class ThreadControllerTests {
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

	private static final int TEST_FORUM_ID = 1;
	private static final int TEST_TEAM_ID = 1;
	private static final int TEST_MANAGER_ID = 1;
	private static final int TEST_THREAD_ID = 3;

	@BeforeEach
	void setup() throws Exception{

		//User
		User user1 = new User();
		user1.setUsername("manager1");
		user1.setPassword("parejo");
		user1.setEnabled(true);
		

		User user2 = new User();
		user2.setUsername("breathOf");
		user2.setPassword("theWild");
		user2.setEnabled(true);
		
		User user3 = new User();
		user3.setUsername("norm");
		user3.setPassword("skully");
		user3.setEnabled(true);
		
		Optional<User> user = Optional.of(new User());
		
		
		//Pilot
		Pilot pt = new Pilot();
		pt.setUser(user2);
		LocalDate date  = LocalDate.now();
		pt.setBirthDate(date);
		pt.setFirstName("Alejandros");
		pt.setLastName("Dumas");
		pt.setDni("23124568D");
		pt.setNationality("Russian");
		pt.setResidence("Moscu");
		pt.setHeight(1.50);
		pt.setWeight(80.2);
		pt.setNumber(11);
		pt.setId(7);
		
		Set<Pilot> setP = new HashSet<>();
		setP.add(pt);
		
		//Mechanic
		Mechanic mc = new Mechanic();
		mc.setUser(user3);
		mc.setBirthDate(date);
		mc.setFirstName("Manuel");
		mc.setLastName("Pica");
		mc.setDni("23124663H");
		mc.setNationality("Italian");
		mc.setResidence("El Vaticano");
		mc.setType(Type.ENGINE);
		mc.setId(9);
		Set<Mechanic> setM = new HashSet<>();
		setM.add(mc);
		
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
		team.setMechanic(setM);
		team.setPilot(setP);
		
		//Forum
		List<Thread> threads = new ArrayList<Thread>();
		Forum forum = new Forum();
		forum.setCreationDate(Date.from(Instant.now()));
		forum.setId(24);
		forum.setName("Test");
		forum.setTeam(team);
		forum.setId(TEST_FORUM_ID);
		//Thread
		List<Message> messages = new ArrayList<Message>();
		Thread thread = new Thread();
		thread.setCreationDate(Date.from(Instant.now()));
		thread.setId(TEST_THREAD_ID);
		thread.setMessages(messages);
		thread.setTitle("Test");
		threads.add(thread);
		forum.setThreads(threads);
	
		given(this.forumService.findForumByTeamId(TEST_TEAM_ID)).willReturn(forum);

		given(this.managerService.findManagerById(TEST_MANAGER_ID)).willReturn(manager1);

		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(team);
		
		given(this.forumService.findForumById(TEST_FORUM_ID)).willReturn(forum);
		
		given(this.managerService.findOwnerByUserName()).willReturn(manager1);
		
		given(this.threadService.findThreadById(TEST_THREAD_ID)).willReturn(thread);
		
		given(this.userService.findPilot()).willReturn(pt);
		
		given(this.userService.findMechanic()).willReturn(mc);
		
		given(this.userService.findUser(user1.getUsername())).willReturn(user);
	}
	
	//Insert new Thread
		@WithMockUser(value = "manager1", authorities = {"manager"})
		@Test
		void testInitNewThread() throws Exception {
			mockMvc.perform(get("/teams/forum/{forumId}/thread/newThread", TEST_FORUM_ID))
			  .andExpect(status().isOk())
			  .andExpect(view().name("threads/createOrUpdateThread"))
			  .andExpect(model().attributeExists("thread"));
		}

		@WithMockUser(value = "manager1", authorities = {"manager"})
		@Test
		void testCreateNewThreadFormSuccess() throws Exception {
			mockMvc.perform(post("/teams/forum/{forumId}/thread/newThread", TEST_FORUM_ID
			)
			.with(csrf())
			.param("title", "Test")).andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/teams/forum/showForum"));
		}
		
		@WithMockUser(value = "manager1", authorities = {"manager"})
		@Test
		void testCreateNewThreadFormHasErrors() throws Exception {
			mockMvc.perform(post("/teams/forum/{forumId}/thread/newThread", TEST_FORUM_ID
			)
			.with(csrf())
			.param("title", "g"))
			.andExpect(model().attributeHasErrors("thread"))
			.andExpect(model().attributeHasFieldErrors("thread", "title"))
			.andExpect(view().name("threads/createOrUpdateThread"));
		}
		
		@WithMockUser(value = "manager1", authorities = {"manager"})
		@Test
		void testCreateNewThreadFormHasErrorsEmptyTitle() throws Exception {
			mockMvc.perform(post("/teams/forum/{forumId}/thread/newThread", TEST_FORUM_ID
			)
			.with(csrf())
			.param("title", ""))
			.andExpect(model().attributeHasErrors("thread"))
			.andExpect(model().attributeHasFieldErrors("thread", "title"))
			.andExpect(view().name("threads/createOrUpdateThread"));
		}
		
//		Thread details
		@WithMockUser(value = "manager1", authorities = {"manager"})
		@Test
		public void testShowThread() throws Exception {
			mockMvc.perform(get("/teams/forum/thread/{threadId}/viewThread", 
					 TEST_THREAD_ID))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("thread"))
			.andExpect(view().name("threads/threadView"));
		}
		
		//Thread delete
		@WithMockUser(value = "manager1", authorities = {"manager"})
		@Test
		void testDeleteThread() throws Exception {
			mockMvc.perform(get("/teams/forum/{forumId}/{threadId}/deleteThread",
					TEST_FORUM_ID, TEST_THREAD_ID))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/welcome"));
		}
}
