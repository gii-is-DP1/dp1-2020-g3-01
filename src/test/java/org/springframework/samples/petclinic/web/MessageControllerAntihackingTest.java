package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.ThreadService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = MessageController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class MessageControllerAntihackingTest {
	
	@MockBean
	private MessageService messageService;

	@MockBean
	private AuthoritiesService authoritiesService;
	
	@MockBean
	private TeamService teamService;
	
	@MockBean
	private UserService userService;

	@MockBean
	private ManagerService managerService;
	
	@MockBean
	private ThreadService threadService;


	@Autowired
	private MockMvc mockMvc;
	
	private static final int TEST_MANAGER_ID = 6;
	private static final int TEST_TEAM_ID = 3;
	private static final int TEST_MESSAGE_ID = 1;
	private static final int TEST_PILOT_ID = 7;
	private static final int TEST_MECHANIC_ID = 9;
	private static final int TEST_THREAD_ID = 5;
//	private static final int TEST_MOTO_ID = 5;
	
	
	@BeforeEach
	void setUp() throws Exception {
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
		
		Pilot pt2 = new Pilot();
		pt2.setUser(user6);
		LocalDate date2  = LocalDate.now();
		pt2.setBirthDate(date2);
		pt2.setFirstName("Alejandra");
		pt2.setLastName("Dumasico");
		pt2.setDni("23124868D");
		pt2.setNationality("Russian");
		pt2.setResidence("Moscu");
		pt2.setHeight(1.50);
		pt2.setWeight(80.2);
		pt2.setNumber(11);
		pt2.setId(18);
		
		Mechanic mc = new Mechanic();
		mc.setUser(user3);
		//LocalDate date  = LocalDate.now();
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
		//LocalDate date  = LocalDate.now();
		mc2.setBirthDate(date);
		mc2.setFirstName("Martos");
		mc2.setLastName("Picaso");
		mc2.setDni("23124763H");
		mc2.setNationality("Italian");
		mc2.setResidence("El Vaticano");
		mc2.setType(Type.ENGINE);
		mc2.setId(10);
		
		Manager m = new Manager();
		m.setUser(user);
		//LocalDate date  = LocalDate.now();
		m.setBirthDate(date);
		m.setFirstName("Jose Antontio");
		m.setLastName("Parejo");
		m.setNationality("Spain");
		m.setResidence("Seville");
		m.setId(6);
		m.setDni("89675432L");
		
		Manager m3 = new Manager();
		m3.setUser(user4);
		//LocalDate date2 = LocalDate.now();
		m3.setBirthDate(date2);
		m3.setFirstName("Fernando");
		m3.setLastName("Alonso");
		m3.setNationality("Spain");
		m3.setResidence("Asturias");
		m3.setId(19);
		m3.setDni("12345678P");
		
		Team team1 = new Team();
		team1.setId(3);
		Date fecha = new Date();
		team1.setCreationDate(fecha);
		team1.setName("Ducati");
		team1.setManager(m);
		team1.setNif("12345678X");
		team1.setPilot(setP);
		
		Collection<Team> teams = new ArrayList<Team>();
		teams.add(team1);
		
		Message message = new Message();
		message.setId(1);
		message.setText("Hola me llamo");
		message.setCreationDate(fecha);
		message.setUser(user);
		message.setTitle("Titulo");
		
		Optional<User> u = Optional.of(new User());
		
		Thread tr = new Thread();
		tr.setCreationDate(fecha);
		tr.setId(5);
		tr.setTitle("Title");
		
		List<Thread> thr = new ArrayList<>();
		thr.add(tr);
		
		List<Message> messages = new ArrayList<>();
		messages.add(message);
		tr.setMessages(messages);

		given(this.threadService.findThreadById(tr.getId())).willReturn(tr);
		
		given(this.managerService.findOwnerByUserName()).willReturn(m3);
		
		given(this.managerService.findManagerById(6)).willReturn(m3);
		
		given(this.teamService.findManager(TEST_MANAGER_ID)).willReturn(team1);
		
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(team1);
		
		given(this.messageService.findMessageById(TEST_MESSAGE_ID)).willReturn(message);
		
		given(this.messageService.findMessageById(message.getId())).willReturn(message);
		
		given(this.userService.findPilot()).willReturn(pt2);
		
		given(this.userService.findMechanic()).willReturn(mc2);
		
		given(this.threadService.findThreadById(TEST_THREAD_ID)).willReturn(tr);
		
		given(this.teamService.findAllTeams()).willReturn(teams);
				
	}
	
	@WithMockUser(value = "jake", authorities = "manager")
	@Test
	void testShouldDontShowAntihackingMessage() throws Exception {
		mockMvc.perform(get("/teams/forum/thread/{threadId}/message/new", TEST_THREAD_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("customMessage"))
		.andExpect(view().name("exception"));
	}

	
	@WithMockUser(value = "jake", authorities = "manager")
	@Test
	void testShouldDontShowAntihackingMessageEdit() throws Exception {
		mockMvc.perform(get("/teams/forum/thread/messages/{messageId}/edit", TEST_MESSAGE_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("customMessage"))
		.andExpect(view().name("exception"));
	}
}
