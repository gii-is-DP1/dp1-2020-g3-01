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

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Motorcycle;
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

@WebMvcTest(controllers = MotorcycleController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class MotorcycleControllerTest {
	
	@MockBean
	private MotorcycleService mmotorcycleService;

	@MockBean
	private AuthoritiesService authoritiesService;
	
	@Autowired
	private TeamController teamController;

	@MockBean
	private TeamService teamService;

	@MockBean
	private ManagerService managerService;
	
	@MockBean
	private PilotService pilotService;


	@Autowired
	private MockMvc mockMvc;
	
	private static final int TEST_MANAGER_ID = 1;
	private static final int TEST_TEAM_ID = 1;
	private static final int TEST_PILOT_ID = 1;
	private static final int TEST_MOTO_ID = 1;
	
	
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
		
		Pilot pt = new Pilot();
		pt.setUser(user2);
		LocalDate date  = LocalDate.now();
		pt.setBirthDate(date);
		pt.setFirstName("Alejandro");
		pt.setLastName("Dumas");
		pt.setDni("23124563D");
		pt.setNationality("Russian");
		pt.setResidence("Moscu");
		pt.setHeight(200.2);
		pt.setWeight(200.2);
		pt.setNumber(11);
		pt.setId(7);
		
		Set<Pilot> setP = new HashSet<>();
		setP.add(pt);
		
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
		
		given(this.managerService.findOwnerByUserName())
		.willReturn(m);
		
		given(this.managerService.findManagerById(6))
		.willReturn(m);
		
		given(this.teamService.findManager(TEST_MANAGER_ID)).willReturn(team1);
		
		//given(this.teamService.getPil(TEST_TEAM_ID)).willReturn(setP);
		
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(team1);
		
	}
	


	@WithMockUser(value = "spring")
	@Test
	void testGetNewMotorcycle() throws Exception {
		mockMvc.perform(get("/managers/{managerId}/teams/{teamId}/pilot/{pilotId}/bikes/new", TEST_MANAGER_ID,TEST_TEAM_ID, TEST_PILOT_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("motorcycle")).
		andExpect(view().name("motorcycle/motorcycleDetails"));
	}
}
