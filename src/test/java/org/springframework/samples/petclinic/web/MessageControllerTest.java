package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
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
import org.springframework.samples.petclinic.model.Motorcycle;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.Type;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.MessageService;
import org.springframework.samples.petclinic.service.MotorcycleService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = MotorcycleController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class MessageControllerTest {
	
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


	@Autowired
	private MockMvc mockMvc;
	
	private static final int TEST_MANAGER_ID = 6;
	private static final int TEST_TEAM_ID = 3;
	private static final int TEST_MESSAGE_ID = 1;
	private static final int TEST_PILOT_ID = 7;
	private static final int TEST_MECHANIC_ID = 9;
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
		
		Team team1 = new Team();
		team1.setId(3);
		Date fecha = new Date();
		team1.setCreationDate(fecha);
		team1.setName("Ducati");
		team1.setManager(m);
		team1.setNif("12345678X");
		team1.setPilot(setP);
		
		
		Message message = new Message();
		message.setId(1);
		message.setText("Hola me llamo");
		message.setCreationDate(fecha);
		message.setUser(user);
		
		
		
		given(this.managerService.findOwnerByUserName()).willReturn(m);
		
		given(this.managerService.findManagerById(6)).willReturn(m);
		
		given(this.teamService.findManager(TEST_MANAGER_ID)).willReturn(team1);
		
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(team1);
		
		given(this.messageService.findMessageById(TEST_MESSAGE_ID)).willReturn(message);
		
		given(this.userService.findPilot()).willReturn(pt);
		
		given(this.userService.findMechanic()).willReturn(mc);
		
		//given(this.userService.findUser(user.getUsername())).willReturn(user);
				
	}
	
	// Message details
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	public void testShowMessage() throws Exception {
		mockMvc.perform(get("managers/{managerId}/teams/{teamId}/forum/{messageId}/details", TEST_MANAGER_ID,TEST_TEAM_ID, TEST_MESSAGE_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("message"))
		.andExpect(view().name("message/messageDetails"));
	}
	
	// Insert new message
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testGetNewMessage() throws Exception {
		mockMvc.perform(get("team/{teamId}/forum/messages/new",TEST_TEAM_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("message")).
		andExpect(view().name("messages/createOrUpdateMessageForm"));
	}

	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testCreateMessageFormSuccess() throws Exception {
		mockMvc.perform(post("team/{teamId}/forum/messages/new", TEST_TEAM_ID)
				.with(csrf())
				.param("id", "2")
				.param("text", "El motor inferior no funciona")
				.param("creationDate", "2020/12/25")
				.param("user.username", "manager5")
				.param("user.password", "manager333"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/welcome"));
	}
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testCreateMechanicFormHasErrors() throws Exception {
		mockMvc.perform(post("team/{teamId}/forum/messages/new", TEST_TEAM_ID)
				.with(csrf())
				.param("id", "2")
				.param("text", "El motor inferior no funciona")
				.param("creationDate", "2023/12/25")
				.param("user.username", "manager5")
				.param("user.password", "manager333"))
				.andExpect(model().attributeHasErrors("message"))
				.andExpect(model().attributeHasFieldErrors("message", "creationDate"))
				.andExpect(status().isOk())
				.andExpect(view().name("redirect:/welcome"));
	}
		
	// Edit motorcycle
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testInitEditMotorcycle() throws Exception {
		mockMvc.perform(get("team/{teamId}/forum/messages/{messageId}/edit", TEST_TEAM_ID, TEST_MESSAGE_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("message"))
		.andExpect(view().name("messages/createOrUpdateMessageForm"));
	}
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testEditMotorcycleSuccess() throws Exception {
		mockMvc.perform(post("team/{teamId}/forum/messages/{messageId}/edit", TEST_TEAM_ID, TEST_MESSAGE_ID)
			.with(csrf())
			.param("text", "El motor inferior no funciona. Acxtualizacion: el motor ya funciona correctamente")
			.param("creationDate", "2020/12/27"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/managers/details"));
	}
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testCreateTeamFormHasErrors() throws Exception {
		mockMvc.perform(post("team/{teamId}/forum/messages/{messageId}/edit", TEST_TEAM_ID, TEST_MESSAGE_ID)
				.with(csrf())
				.param("text", "El motor inferior no funciona. Acxtualizacion: el motor ya funciona correctamente")
				.param("creationDate", "2020/12/24")
				.param("user.username", "mechanic5")
				.param("user.password", "mechanic333"))
				.andExpect(model().attributeHasErrors("message"))
				.andExpect(model().attributeHasFieldErrors("message", "creationDate"))
				.andExpect(model().attributeHasFieldErrors("message", "user.username"))
				.andExpect(model().attributeHasFieldErrors("message", "user.password"))
				.andExpect(status().isOk())
				.andExpect(view().name("redirect:/managers/details"));
	}

}