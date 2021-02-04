package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.MotorcycleService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PilotController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class PilotControllerAntihackingTest {

	@MockBean
	private TeamService teamService;

	@MockBean
	private ManagerService managerService;

	@MockBean
	private PilotService pilotService;

	@MockBean
	private MotorcycleService motorcycleService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	// private Team team1;

	private static final int TEST_MANAGER_ID = 6;
	private static final int TEST_TEAM_ID = 3;
	private static final int TEST_PILOT_ID = 7;

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
		user3.setUsername("pepito");
		user3.setPassword("ferrari123");
		user3.setEnabled(true);

		Manager m3 = new Manager();
		m3.setUser(user3);
		LocalDate date2 = LocalDate.now();
		m3.setBirthDate(date2);
		m3.setFirstName("Fernando");
		m3.setLastName("Alonso");
		m3.setNationality("Spain");
		m3.setResidence("Asturias");
		m3.setId(7);
		m3.setDni("12345678P");

		Pilot pilot = new Pilot();
		pilot.setUser(user2);
		LocalDate date = LocalDate.now();
		pilot.setBirthDate(date);
		pilot.setFirstName("Alejandro");
		pilot.setLastName("Dumas");
		pilot.setDni("23124563D");
		pilot.setNumber(23);
		pilot.setHeight(1.65);
		pilot.setWeight(65.3);
		pilot.setNationality("Russian");
		pilot.setResidence("Moscu");
		pilot.setId(7);

		Set<Pilot> setP = new HashSet<>();
		setP.add(pilot);

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

		Team team1 = new Team();
		team1.setId(3);
		Date fecha = new Date();
		team1.setCreationDate(fecha);
		team1.setName("Ducati");
		team1.setManager(m);
		team1.setNif("12345678X");
		team1.setPilot(setP);

		given(this.managerService.findOwnerByUserName()).willReturn(m3);

		given(this.managerService.findManagerById(6)).willReturn(m);

		given(this.teamService.findManager(TEST_MANAGER_ID)).willReturn(team1);

		given(this.teamService.getPilotsById(TEST_TEAM_ID)).willReturn(setP);

		given(this.pilotService.findById(TEST_PILOT_ID)).willReturn(pilot);

		// given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(team1);

	}

	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testShouldNotShowNewPilotForm() throws Exception {
		mockMvc.perform(get("/managers/{managerId}/teams/{teamId}/pilots/new", TEST_MANAGER_ID, TEST_TEAM_ID))
				.andExpect(status().isOk()).andExpect(model().attributeExists("customMessage"))
				.andExpect(view().name("exception"));
	}

	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testShouldNotShowEditPilotForm() throws Exception {
		mockMvc.perform(get("/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/update", TEST_MANAGER_ID,
				TEST_TEAM_ID, TEST_PILOT_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("customMessage")).andExpect(view().name("exception"));
	}

}
