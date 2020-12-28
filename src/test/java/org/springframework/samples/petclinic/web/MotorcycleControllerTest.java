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

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
	private MotorcycleService motorcycleService;

	@MockBean
	private AuthoritiesService authoritiesService;
	
	@MockBean
	private TeamService teamService;

	@MockBean
	private ManagerService managerService;
	
	@MockBean
	private PilotService pilotService;


	@Autowired
	private MockMvc mockMvc;
	
	private static final int TEST_MANAGER_ID = 6;
	private static final int TEST_TEAM_ID = 3;
	private static final int TEST_PILOT_ID = 7;
	private static final int TEST_MOTO_ID = 5;
	
	
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
		
		
		Motorcycle bike = new Motorcycle();
		bike.setId(5);
		bike.setBrand("KATM");
		bike.setDisplacement(1982);
		bike.setHorsePower(200);
		bike.setMaxSpeed(210.2);
		bike.setPilot(pt);
		bike.setTankCapacity(58.9);
		bike.setWeight(79);
		
		
		given(this.managerService.findOwnerByUserName()).willReturn(m);
		
		given(this.managerService.findManagerById(6)).willReturn(m);
		
		given(this.motorcycleService.countBikes(TEST_PILOT_ID)).willReturn(0);
		
		given(this.teamService.findManager(TEST_MANAGER_ID)).willReturn(team1);
		
		//given(this.teamService.getPilotsById(TEST_TEAM_ID)).willReturn(setP);
		
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(team1);
		
		//given(this.pilotService.findById(TEST_PILOT_ID)).willReturn(pt);
		
		given(this.motorcycleService.findMotorcycleById(TEST_MOTO_ID)).willReturn(bike);
		
		given(this.teamService.searchPilot(TEST_PILOT_ID)).willReturn(pt);
		
	}
	
	// Motorcycle details
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	public void testShowMoto() throws Exception {
		mockMvc.perform(get("/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/bikes/{motorcycleId}/details", TEST_MANAGER_ID,TEST_TEAM_ID, TEST_PILOT_ID, TEST_MOTO_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("motorcycle"))
		.andExpect(view().name("motorcycle/motorcycleDetails"));
	}
	
	// Insert new motorcycle
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testGetNewMotorcycle() throws Exception {
		mockMvc.perform(get("/managers/{managerId}/teams/{teamId}/pilot/{pilotId}/bikes/new", TEST_MANAGER_ID, TEST_TEAM_ID, TEST_PILOT_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("motorcycle")).
		andExpect(view().name("teams/createOrUpdateBikeForm"));
	}

	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testCreateMotorcycleFormSuccess() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilot/{pilotId}/bikes/new", TEST_MANAGER_ID, TEST_TEAM_ID, TEST_PILOT_ID)
				.with(csrf())
				.param("id", "9")
				.param("brand", "KTM")
				.param("displacement", "1900")
				.param("horsePower", "300")
				.param("weight", "160")
				.param("tankCapacity", "21")
				.param("maxSpeed", "350")
				.param("pilot", "7"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/motorcycle/9/details"));
	}
	
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testCreateMotorcycleFormHasErrors() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilot/{pilotId}/bikes/new", TEST_MANAGER_ID, TEST_TEAM_ID, TEST_PILOT_ID)
				.with(csrf())
				.param("id", "5")
				.param("brand", "KTM")
				.param("displacement", "1900")
				.param("horsePower", "300")
				.param("weight", "456")
				.param("tankCapacity", "21")
				.param("maxSpeed", "350")
				.param("pilot", "7"))
				.andExpect(model().attributeHasErrors("motorcycle"))
				.andExpect(model().attributeHasFieldErrors("motorcycle", "weight"))
				.andExpect(status().isOk())
				.andExpect(view().name("teams/createOrUpdateBikeForm"));
	}
	
	// Edit motorcycle
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testInitEditMotorcycle() throws Exception {
		mockMvc.perform(get("/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/bikes/{motorcycleId}/edit", TEST_MANAGER_ID, TEST_TEAM_ID, TEST_PILOT_ID, TEST_MOTO_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("motorcycle"))
		.andExpect(view().name("teams/createOrUpdateBikeForm"));
	}
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testEditMotorcycleSuccess() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/bikes/{motorcycleId}/edit", TEST_MANAGER_ID, TEST_TEAM_ID, TEST_PILOT_ID, TEST_MOTO_ID)
			.with(csrf())
			.param("brand", "Yamahaaaa")
			.param("displacement", "2000")
			.param("weight", "150")
			.param("maxSpeed", "340")
			.param("pilot", "7")
			)
		 	//.andExpect(status().is3xxRedirection())		
			.andExpect(view().name("motorcycle/motorcycleDetails"))
			.andExpect(status().isOk());
		}

}