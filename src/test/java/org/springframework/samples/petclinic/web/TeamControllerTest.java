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
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.Type;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ForumService;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.MechanicService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.ThreadService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(controllers = TeamController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
class TeamControllerTest {

	@Autowired
	private TeamController teamController;

	@MockBean
	private TeamService teamService;

	@MockBean
	private ManagerService managerService;
	
	@MockBean
	private MechanicService mechanicService;

	@MockBean
	private AuthoritiesService authoritiesService;
	
	@MockBean
	private ForumService forumService;

	@Autowired
	private MockMvc mockMvc;

	//private Team team1;

	private static final int TEST_MANAGER_ID = 6;
	private static final int TEST_TEAM_ID = 3;

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
		
		Mechanic mc = new Mechanic();
		mc.setUser(user2);
		LocalDate date  = LocalDate.now();
		mc.setBirthDate(date);
		mc.setFirstName("Alejandro");
		mc.setLastName("Dumas");
		mc.setDni("23124563D");
		mc.setNationality("Russian");
		mc.setResidence("Moscu");
		mc.setType(Type.ENGINE);
		mc.setId(7);
		
		Set<Mechanic> setM = new HashSet<>();
		setM.add(mc);
		
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
		team1.setMechanic(setM);
		
		given(this.managerService.findOwnerByUserName())
		.willReturn(m);
		
		given(this.managerService.findManagerById(6))
		.willReturn(m);
		
		given(this.teamService.findManager(TEST_MANAGER_ID)).willReturn(team1);
		
		given(this.teamService.getMechanicsById(TEST_TEAM_ID)).willReturn(setM);
		
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(team1);
		
		
	}
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testShowTeam() throws Exception {
		mockMvc.perform(get("/managers/{managerId}/teams/{teamId}/details", TEST_MANAGER_ID,TEST_TEAM_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("team"))
		.andExpect(view().name("teams/teamDetails"));
	}
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testGetNewTeam() throws Exception {
		mockMvc.perform(get("/managers/{managerId}/teams/new", TEST_MANAGER_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("team")).
		andExpect(view().name("teams/createOrUpdateTeamForm"));
	}

	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testInitEditTeam() throws Exception {
		mockMvc.perform(get("/managers/{managerId}/teams/{teamId}/edit", TEST_MANAGER_ID,TEST_TEAM_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("team"))
		.andExpect(view().name("teams/createOrUpdateTeamForm"));
	}
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testCreateTeamFormSuccess() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/new", TEST_MANAGER_ID,TEST_TEAM_ID)
				.with(csrf())
				.param("name", "Las Divinas2")
				.param("creationDate", "1960/01/01")
				.param("nif", "12345628D")
				.param("manager", "6"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/welcome"));
	}
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testCreateTeamFormHasErrors() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/new", TEST_MANAGER_ID,TEST_TEAM_ID)
				.with(csrf())
				.param("name", "L")
				.param("creationDate", "1960/01/01")
				.param("nif", "12345K78D")
				.param("manager", "6"))
				.andExpect(model().attributeHasErrors("team"))
				.andExpect(model().attributeHasFieldErrors("team", "name"))
				.andExpect(model().attributeHasFieldErrors("team", "nif"))
				.andExpect(status().isOk())
				.andExpect(view().name("teams/createOrUpdateTeamForm"));
	}

	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	//@Transactional
	void testEditTeamSuccess() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/edit", TEST_MANAGER_ID,TEST_TEAM_ID)
			.with(csrf())
			.param("name", "Las Divinas2")
			.param("creationDate", "1960/01/01")
			.param("nif", "12345628D")
			.param("manager", "6"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/managers/6/teams/3/details"));
	}
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testEditTeamHasErrors() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/edit", TEST_MANAGER_ID,TEST_TEAM_ID)
			.with(csrf())
			.param("name", "La")
			.param("creationDate", "1960/01/01")
			.param("nif", "1234562448D")
			.param("manager", "6"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("team"))
			.andExpect(model().attributeHasFieldErrors("team", "name"))
			.andExpect(model().attributeHasFieldErrors("team", "nif"))
			.andExpect(view().name("teams/createOrUpdateTeamForm"));
	}
	
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testCreateMechanicFormSuccess() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/mechanics/new", TEST_MANAGER_ID,TEST_TEAM_ID)
				.with(csrf())
				.param("firstName", "Antonio")
				.param("lastName", "Banderas")
				.param("birthDate", "1960/01/01")
				.param("residence", "CUEVA")
				.param("nationality", "SPAIN")
				.param("dni", "11223344A")
				.param("type", "ENGINE")
				.param("user.username", "mechanic6")
				.param("user.password", "thisisperfectpasswrod123"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/welcome"));
	}
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testCreateMechanicFormHasErrors() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/mechanics/new", TEST_MANAGER_ID,TEST_TEAM_ID)
				.with(csrf())
				.param("firstName", "Antonio")
				.param("lastName", "Banderas")
				.param("birthDate", "1960/01/01")
				.param("residence", "CUEVA")
				.param("nationality", "SPAIN")
				.param("dni", "1hsdfkj3344A")
				.param("type", "ENGINE")
				.param("user.username", "mechanic6")
				.param("user.password", "thisisperfectpasswrod123"))
				.andExpect(model().attributeHasErrors("mechanic"))
				.andExpect(model().attributeHasFieldErrors("mechanic", "dni"))
				.andExpect(status().isOk())
				.andExpect(view().name("mechanics/createOrUpdateMechanicForm"));
	}
	
	//Delete a Team
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testDeleteTeam() throws Exception {
		mockMvc.perform(get("/managers/{managerId}/teams/{teamId}/remove", TEST_MANAGER_ID,TEST_TEAM_ID))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/managers/details"));
	}



}
