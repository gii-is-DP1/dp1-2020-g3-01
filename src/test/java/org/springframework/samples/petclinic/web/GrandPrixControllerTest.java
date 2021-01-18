package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.Instant;
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
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Motorcycle;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.GrandPrixService;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.MotorcycleService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = GrandPrixController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class GrandPrixControllerTest {

	@MockBean
	private GrandPrixService grandPrixService;

	@MockBean
	private AuthoritiesService authoritiesService;
	
	@MockBean
	private PilotService pilotService;
	
	@MockBean
	private TeamService teamService;
	
	@MockBean
	private ManagerService managerService;
	
	@MockBean
	private MotorcycleService motorcycleService;

	@Autowired
	private MockMvc mockMvc;
	
	private static final int TEST_GRANDPRIX_ID = 9;
	private static final int TEST_MANAGER_ID = 9;
	private static final int TEST_PILOT_ID = 11;
	private static final int TEST_BIKE_ID = 3;
	private static final int TEST_GRANDPRIX_ID2 = 10;

	@BeforeEach
	void setUp() throws Exception {
		User user = new User();
		Authorities auth = new Authorities();
		user.setUsername("admin1");
		user.setPassword("4dm1n");
		user.setEnabled(true);
		auth.setId(1);
		auth.setUser(user);
		auth.setAuthority("admin");
		
		User user2 = new User();
		Authorities auth2 = new Authorities();
		user.setUsername("manager1");
		user.setPassword("m4n4g3r1");
		user.setEnabled(true);
		auth.setId(TEST_MANAGER_ID);
		auth.setUser(user2);
		auth.setAuthority("manager");
		
		User user3 = new User();
		Authorities auth3 = new Authorities();
		user.setUsername("pilot1");
		user.setPassword("p1l0t1");
		user.setEnabled(true);
		auth.setId(7);
		auth.setUser(user3);
		auth.setAuthority("pilot");
		
		Pilot pilot = new Pilot();
		pilot.setBirthDate(LocalDate.of(1972, 6, 10));
		pilot.setDni("87654321H");
		pilot.setFirstName("Guillermo");
		pilot.setHeight(1.8);
		pilot.setId(TEST_PILOT_ID);
		pilot.setLastName("Diaz");
		pilot.setNationality("Spain");
		pilot.setNumber(96);
		pilot.setResidence("Andorra");
		pilot.setUser(user3);
		pilot.setWeight(85.);
		
		Set<Pilot> sp = new HashSet<Pilot>();
		sp.add(pilot);
		
		Motorcycle moto = new Motorcycle();
		moto.setBrand("Piaggio");
		moto.setDisplacement(1670);
		moto.setHorsePower(399);
		moto.setId(TEST_BIKE_ID);
		moto.setMaxSpeed(300.);
		moto.setPilot(pilot);
		moto.setTankCapacity(18.);
		moto.setWeight(250);
		
		Manager manager = new Manager();
		manager.setBirthDate(LocalDate.of(1999, 8, 24));
		manager.setDni("12345678Q");
		manager.setFirstName("Nombre");
		manager.setId(9);
		manager.setLastName("Apellido");
		manager.setNationality("Nacionalidad");
		manager.setResidence("Residencia");
		manager.setUser(user2);
		
		Team team = new Team();
		team.setCreationDate(Date.from(Instant.now()));
		team.setId(5);
		team.setManager(manager);
		team.setName("Nombre");
		team.setNif("12345678Q");
		team.setPilot(sp);
		
		Set<Team> st = new HashSet<Team>();
		Set<Team> st2 = new HashSet<Team>();
		st2.add(team);
		
		GrandPrix gp = new GrandPrix();
		gp.setCircuit("Circuit");
		gp.setDayOfRace(Date.from(Instant.now()));
		gp.setDistance(100.);
		gp.setId(TEST_GRANDPRIX_ID);
		gp.setLaps(7);
		gp.setLocation("Location");
		gp.setTeam(st);
		
		GrandPrix gp2 = new GrandPrix();
		gp2.setCircuit("Circuit");
		gp2.setDayOfRace(Date.from(Instant.now()));
		gp2.setDistance(100.);
		gp2.setId(TEST_GRANDPRIX_ID2);
		gp2.setLaps(7);
		gp2.setLocation("Location");
		gp2.setTeam(st2);
		
		given(this.grandPrixService.findGPById(TEST_GRANDPRIX_ID)).willReturn(gp);
		
		given(this.grandPrixService.findGPById(TEST_GRANDPRIX_ID2)).willReturn(gp2);
		
		given(this.teamService.findManager(TEST_MANAGER_ID)).willReturn(team);
		
		given(this.motorcycleService.findMotorcycleById(TEST_BIKE_ID)).willReturn(moto);
		
		given(this.motorcycleService.findMotorcycleByPilotId(TEST_PILOT_ID)).willReturn(moto);
		
		given(this.pilotService.findById(TEST_PILOT_ID)).willReturn(pilot);
		
		given(this.managerService.findOwnerByUserName()).willReturn(manager);

	}

	// Historia de usuario 16 - Listado de todos los gran premios

	@WithMockUser(value = "admin1", authorities = "admin")
	@Test
	void testListAllGP() throws Exception {
		mockMvc.perform(get("/grandprix/all")).andExpect(status().isOk())
				.andExpect(model().attributeExists("grandPrix")).andExpect(view().name("grandprix/list"));
	}
	
	// GrandPrix Details
	@WithMockUser(value = "admin1", authorities = "admin")
	@Test
	void testShowGP() throws Exception {
		mockMvc.perform(get("/grandprix/{grandPrixId}/details", TEST_GRANDPRIX_ID))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("grandPrix"))
			.andExpect(view().name("grandprix/grandPrixDetails"));
	}
	
	
	// Create a new GP
	@WithMockUser(value = "admin1", authorities = "admin")
	@Test
	void testInitCreateNewGP() throws Exception {
		mockMvc.perform(get("/grandprix/new"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("grandPrix"))
			.andExpect(view().name("grandprix/createOrUpdateGrandPrix"));
	}
	
	@WithMockUser(value = "admin1", authorities = "admin")
	@Test
	void testCreateFormGPSuccess() throws Exception {
		mockMvc.perform(post("/grandprix/new").with(csrf())
				.param("circuit", "Circuit")
				.param("dayOfRace", "2021/2/12")
				.param("distance", "100.0")
				.param("id", "9")
				.param("laps", "7")
				.param("location", "Location"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/grandprix/all"));
	}
	
	@WithMockUser(value = "admin1", authorities = "admin")
	@Test
	void testCreateFormGPHasErrors() throws Exception {
		mockMvc.perform(post("/grandprix/new").with(csrf())
				.param("circuit", "Circuit")
				.param("dayOfRace", "2021/2/12")
				.param("distance", "100.0")
				.param("id", "9")
				.param("laps", "-1")
				.param("location", "Location"))
			.andExpect(model().attributeHasErrors("grandPrix"))
			.andExpect(model().attributeHasFieldErrors("grandPrix", "laps"))
			.andExpect(status().isOk())
			.andExpect(view().name("grandprix/createOrUpdateGrandPrix"));
	}
	
	//Edit a GrandPrix
	
	@WithMockUser(value = "admin1", authorities = "admin")
	@Test
	void testInitEditNewGP() throws Exception {
		mockMvc.perform(get("/grandprix/{grandPrixId}/edit", TEST_GRANDPRIX_ID))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("grandPrix"))
			.andExpect(view().name("/grandprix/createOrUpdateGrandPrix"));
	}
	
	@WithMockUser(value = "admin1", authorities = "admin")
	@Test
	void testEditFormGPSuccess() throws Exception {
		mockMvc.perform(post("/grandprix/{grandPrixId}/edit", TEST_GRANDPRIX_ID).with(csrf())
				.param("circuit", "Circuito")
				.param("dayOfRace", "2021/2/12")
				.param("distance", "150.0")
				.param("id", "9")
				.param("laps", "7")
				.param("location", "Location"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/grandprix/{grandPrixId}/details"));
	}
	
	@WithMockUser(value = "admin1", authorities = "admin")
	@Test
	void testEditFormGPHasErrors() throws Exception {
		mockMvc.perform(post("/grandprix/new").with(csrf())
				.param("circuit", "Circuit")
				.param("dayOfRace", "2018/2/12")
				.param("distance", "100.0")
				.param("id", "9")
				.param("laps", "50")
				.param("location", "Location"))
			.andExpect(model().attributeHasErrors("grandPrix"))
			.andExpect(model().attributeHasFieldErrors("grandPrix", "laps"))
			.andExpect(status().isOk())
			.andExpect(view().name("grandprix/createOrUpdateGrandPrix"));
	}
	
	//Delete a GrandPrix
	
	@WithMockUser(value = "admin1", authorities = "admin")
	@Test
	void testDeleteGP() throws Exception {
		mockMvc.perform(get("/grandprix/{grandPrixId}/remove", TEST_GRANDPRIX_ID))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/grandprix/all"));
	}
	
	//Inscribe a Team in a GP
	
	@WithMockUser(value = "manager1", authorities = "manager")
	@Test
	void testAddTeam() throws Exception {
		mockMvc.perform(get("/grandprix/{grandPrixId}/addTeam", TEST_GRANDPRIX_ID))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/welcome"));
	}
	
	//Remove a Team from a GP
	
	@WithMockUser(value = "manager1", authorities = "manager")
	@Test
	void testRemoveTeam() throws Exception {
		mockMvc.perform(get("/grandprix/{grandPrixId}/removeTeam", TEST_GRANDPRIX_ID2))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/welcome"));
	}
	

}