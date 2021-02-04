package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
import org.springframework.samples.petclinic.model.Thread;
import org.springframework.samples.petclinic.model.Type;
import org.springframework.samples.petclinic.model.User;
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

@WebMvcTest(controllers = ThreadController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class ThreadControllerAntihackingTest {
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
	void setup() throws Exception {

		User user = new User();
		user.setUsername("jantontio");
		user.setPassword("parejo");
		user.setEnabled(true);

		User user2 = new User();
		user2.setUsername("breathOf");
		user2.setPassword("theWild");
		user2.setEnabled(true);

		User user3 = new User();
		user3.setUsername("norm");
		user3.setPassword("skully");
		user3.setEnabled(true);

		User user4 = new User();
		user4.setUsername("jake");
		user4.setPassword("peralta");
		user4.setEnabled(true);

		User user5 = new User();
		user5.setUsername("amy");
		user5.setPassword("santiago");
		user5.setEnabled(true);

		User user6 = new User();
		user6.setUsername("raphael");
		user6.setPassword("turtle");
		user6.setEnabled(true);

		Optional<User> user1 = Optional.of(new User());
		
		Pilot pt = new Pilot();
		pt.setUser(user2);
		LocalDate date = LocalDate.now();
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

		Pilot pt2 = new Pilot();
		pt2.setUser(user6);
		LocalDate date2 = LocalDate.now();
		pt.setBirthDate(date2);
		pt.setFirstName("Alejandra");
		pt.setLastName("Dumasico");
		pt.setDni("23124868D");
		pt.setNationality("Russian");
		pt.setResidence("Moscu");
		pt.setHeight(1.50);
		pt.setWeight(80.2);
		pt.setNumber(11);
		pt.setId(18);

		Mechanic mc = new Mechanic();
		mc.setUser(user3);
		// LocalDate date = LocalDate.now();
		mc.setBirthDate(date);
		mc.setFirstName("Manuel");
		mc.setLastName("Pica");
		mc.setDni("23124663H");
		mc.setNationality("Italian");
		mc.setResidence("El Vaticano");
		mc.setType(Type.ENGINE);
		mc.setId(9);

		Mechanic mc2 = new Mechanic();
		mc.setUser(user5);
		// LocalDate date = LocalDate.now();
		mc2.setBirthDate(date);
		mc2.setFirstName("Martos");
		mc2.setLastName("Picaso");
		mc2.setDni("23124763H");
		mc2.setNationality("Italian");
		mc2.setResidence("El Vaticano");
		mc2.setType(Type.ENGINE);
		mc2.setId(10);

		Set<Mechanic> setM = new HashSet<>();
		setM.add(mc);
		
		
		Manager m = new Manager();
		m.setUser(user);
		// LocalDate date = LocalDate.now();
		m.setBirthDate(date);
		m.setFirstName("Jose Antontio");
		m.setLastName("Parejo");
		m.setNationality("Spain");
		m.setResidence("Seville");
		m.setId(6);
		m.setDni("89675432L");

		Manager m3 = new Manager();
		m3.setUser(user4);
		// LocalDate date2 = LocalDate.now();
		m3.setBirthDate(date2);
		m3.setFirstName("Fernando");
		m3.setLastName("Alonso");
		m3.setNationality("Spain");
		m3.setResidence("Asturias");
		m3.setId(19);
		m3.setDni("12345678P");

		// Team
		Team team = new Team();
		team.setCreationDate(Date.from(Instant.now()));
		team.setManager(m);
		team.setName("DELLAFUENTE M.C.");
		team.setNif("13181318D");
		team.setId(TEST_TEAM_ID);
		team.setMechanic(setM);
		team.setPilot(setP);

		// Forum
		List<Thread> threads = new ArrayList<Thread>();
		Forum forum = new Forum();
		forum.setCreationDate(Date.from(Instant.now()));
		forum.setId(24);
		forum.setName("Test");
		forum.setTeam(team);
		forum.setId(TEST_FORUM_ID);
		// Thread
		List<Message> messages = new ArrayList<Message>();
		Thread thread = new Thread();
		thread.setCreationDate(Date.from(Instant.now()));
		thread.setId(TEST_THREAD_ID);
		thread.setMessages(messages);
		thread.setTitle("Test");
		threads.add(thread);
		forum.setThreads(threads);

		given(this.forumService.findForumByTeamId(TEST_TEAM_ID)).willReturn(forum);

		given(this.managerService.findManagerById(TEST_MANAGER_ID)).willReturn(m3);

		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(team);

		given(this.forumService.findForumById(TEST_FORUM_ID)).willReturn(forum);

		given(this.managerService.findOwnerByUserName()).willReturn(m3);

		given(this.threadService.findThreadById(TEST_THREAD_ID)).willReturn(thread);

		given(this.userService.findPilot()).willReturn(pt2);

		given(this.userService.findMechanic()).willReturn(mc2);

		given(this.userService.findUser(user.getUsername())).willReturn(user1);
	}

	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testShouldDontShowAntihackingThread() throws Exception {
		mockMvc.perform(get("/teams/forum/{forumId}/thread/newThread", TEST_FORUM_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("customMessage"))
				.andExpect(view().name("exception"));
	}
}
