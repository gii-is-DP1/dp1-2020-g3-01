package org.springframework.samples.petclinic.web;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
import org.springframework.samples.petclinic.model.Motorcycle;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.MotorcycleService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


@WebMvcTest(value=PilotController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class PilotControllerTest {
	
	@MockBean
	private TeamService teamService;
	
	@MockBean
	private ManagerService managerService;
	
	@MockBean
	private PilotService pilotService;
	
	@MockBean MotorcycleService motoService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private static final int TEST_MANAGER_ID = 1;

	private static final int TEST_TEAM_ID = 1;
	
	private static final int TEST_PILOT_ID = 1;
	
	private static final int TEST_MOTORCYCLE_ID = 1;
	@BeforeEach
	
	void setUp() {
		
		Pilot pilot1 = new Pilot();
		User userPilot1 = new User();
		LocalDate birthday = LocalDate.of(98, 06, 22);
		userPilot1.setUsername("piloto1");
		userPilot1.setPassword("piloto1pass");
		userPilot1.setEnabled(true);
		pilot1.setUser(userPilot1);
		pilot1.setBirthDate(birthday);
		pilot1.setDni("65478987D");
		pilot1.setFirstName("Marcos");
		pilot1.setHeight(1.87);
		pilot1.setId(TEST_PILOT_ID);
		pilot1.setLastName("Alonso");
		pilot1.setNationality("Camboya");
		pilot1.setNumber(89);
		pilot1.setResidence("Sevilla");
		pilot1.setWeight(82.0);
		Set<Pilot> sp = new HashSet<>();
		sp.add(pilot1);
		
		Manager manager1 = new Manager();
		User userManager1 = new User();
		userManager1.setUsername("manager1");
		userManager1.setPassword("manager1pass");
		userManager1.setEnabled(true);
		manager1.setUser(userManager1);
		manager1.setBirthDate(birthday);
		manager1.setDni("87976598D");
		manager1.setFirstName("Antonio");
		manager1.setId(TEST_MANAGER_ID);
		manager1.setLastName("Peres");
		manager1.setNationality("Spain");
		manager1.setResidence("Sevilla");
		
		Team team1 = new Team();
		Date creation = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		team1.setCreationDate(creation);
		team1.setId(TEST_TEAM_ID);
		team1.setManager(manager1);
		team1.setName("Equipo1");
		team1.setNif("98765423F");
		team1.setPilot(sp);
		
		Motorcycle moto1 = new Motorcycle();
		moto1.setId(TEST_MOTORCYCLE_ID);
		
		given(this.teamService.findTeamById(TEST_TEAM_ID)).willReturn(team1);
		given(this.managerService.findOwnerByUserName()).willReturn(manager1);
		given(this.pilotService.findById(pilot1.getId())).willReturn(pilot1);
		given(this.motoService.findMotorcycleById(TEST_MOTORCYCLE_ID)).willReturn(moto1);
	}
	
	//Pilot Details
	@WithMockUser(value="manager1", authorities={"manager"})
	@Test
	void testShowPilot() throws Exception {
		mockMvc.perform(get("/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/details",TEST_MANAGER_ID,TEST_TEAM_ID,TEST_PILOT_ID))
				.andExpect(status().isOk())
				.andExpect(view().name("pilots/details"))
				.andExpect(model().attributeExists("pilot"));
	}
	
	//Create a new Pilot
	@WithMockUser(value="manager1", authorities={"manager"})
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/managers/{managerId}/teams/{teamId}/pilots/new",TEST_MANAGER_ID,TEST_TEAM_ID)).
		andExpect(status().isOk())
		.andExpect(view().name("pilots/create"))
		.andExpect(model().attributeExists("pilot"));
	}
	
	//Create Pilot con valores correctos
	@WithMockUser(value="manager1", authorities={"manager"})
	@Test
	void testProcessCreationForm() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilots/new",TEST_MANAGER_ID,TEST_TEAM_ID).with(csrf())
				.param("firstName","Pedro")
				.param("lastName", "Sanchez")
				.param("birthDate", "1998/06/22")
				.param("residence", "Sevilla")
				.param("nationality", "Camboya")
				.param("number", "32")
				.param("height", "1.89")
				.param("weight", "80.0")
				.param("dni", "47576890G")
				.param("user.username", "ps98")
				.param("user.password", "secreto"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/welcome"));
	}
	
	//Crear Pilot con valores dni incorrectos
	@WithMockUser(value = "manager1", authorities = "manager")
	@Test
	void testCreateFormPilotHasErrorsDni() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilots/new",TEST_MANAGER_ID,TEST_TEAM_ID).with(csrf())
				.param("firstName","Pedro")
				.param("lastName", "Sanchez")
				.param("birthDate", "2020/06/22")
				.param("residence", "Sevilla")
				.param("nationality", "Camboya")
				.param("number", "32")
				.param("height", "1.89")
				.param("weight", "80.0")
				.param("dni", "47576890GD")
				.param("user.username", "ps98")
				.param("user.password", "secreto"))
			.andExpect(model().attributeHasErrors("pilot"))
			.andExpect(model().attributeHasFieldErrors("pilot", "dni"))
			.andExpect(status().isOk())
			.andExpect(view().name("pilots/create"));
	}
	
	//Crear Pilot con valores birthDate incorrectos
	@WithMockUser(value = "manager1", authorities = "manager")
	@Test
	void testCreateFormPilotHasErrorsBirthDate() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilots/new",TEST_MANAGER_ID,TEST_TEAM_ID).with(csrf())
				.param("firstName","Pedro")
				.param("lastName", "Sanchez")
				.param("birthDate", "2020/06/22")
				.param("residence", "Sevilla")
				.param("nationality", "Camboya")
				.param("number", "32")
				.param("height", "1.89")
				.param("weight", "80.0")
				.param("dni", "47576890G")
				.param("user.username", "ps98")
				.param("user.password", "secreto"))
			.andExpect(model().attributeHasErrors("pilot"))
			.andExpect(model().attributeHasFieldErrors("pilot", "birthDate"))
			.andExpect(status().isOk())
			.andExpect(view().name("pilots/create"));
	}
	
	
	//Crear Pilot con valores number incorrectos negativo
	@WithMockUser(value = "manager1", authorities = "manager")
	@Test
	void testCreateFormPilotHasErrorsNumbersNegative() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilots/new",TEST_MANAGER_ID,TEST_TEAM_ID).with(csrf())
				.param("firstName","Pedro")
				.param("lastName", "Sanchez")
				.param("birthDate", "1998/06/22")
				.param("residence", "Sevilla")
				.param("nationality", "Camboya")
				.param("number", "-32")
				.param("height", "1.89")
				.param("weight", "80.0")
				.param("dni", "47576890G")
				.param("user.username", "ps98")
				.param("user.password", "secreto"))
			.andExpect(model().attributeHasErrors("pilot"))
			.andExpect(model().attributeHasFieldErrors("pilot", "number"))
			.andExpect(status().isOk())
			.andExpect(view().name("pilots/create"));
	}
	
	//Crear Pilot con valores number incorrectos
	@WithMockUser(value = "manager1", authorities = "manager")
	@Test
	void testCreateFormPilotHasErrorsNumbers() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilots/new",TEST_MANAGER_ID,TEST_TEAM_ID).with(csrf())
				.param("firstName","Pedro")
				.param("lastName", "Sanchez")
				.param("birthDate", "1998/06/22")
				.param("residence", "Sevilla")
				.param("nationality", "Camboya")
				.param("number", "100")
				.param("height", "1.89")
				.param("weight", "80.0")
				.param("dni", "47576890G")
				.param("user.username", "ps98")
				.param("user.password", "secreto"))
			.andExpect(model().attributeHasErrors("pilot"))
			.andExpect(model().attributeHasFieldErrors("pilot", "number"))
			.andExpect(status().isOk())
			.andExpect(view().name("pilots/create"));
	}
	
	//Crear Pilot con valores height incorrectos
	@WithMockUser(value = "manager1", authorities = "manager")
	@Test
	void testCreateFormPilotHasErrorsHeight() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilots/new",TEST_MANAGER_ID,TEST_TEAM_ID).with(csrf())
				.param("firstName","Pedro")
				.param("lastName", "Sanchez")
				.param("birthDate", "1998/06/22")
				.param("residence", "Sevilla")
				.param("nationality", "Camboya")
				.param("number", "32")
				.param("height", "3.0")
				.param("weight", "80.0")
				.param("dni", "47576890G")
				.param("user.username", "ps98")
				.param("user.password", "secreto"))
			.andExpect(model().attributeHasErrors("pilot"))
			.andExpect(model().attributeHasFieldErrors("pilot", "height"))
			.andExpect(status().isOk())
			.andExpect(view().name("pilots/create"));
	}
	
	//Crear Pilot con valores height incorrectos negativos
	@WithMockUser(value = "manager1", authorities = "manager")
	@Test
	void testCreateFormPilotHasErrorsHeightNegative() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilots/new",TEST_MANAGER_ID,TEST_TEAM_ID).with(csrf())
				.param("firstName","Pedro")
				.param("lastName", "Sanchez")
				.param("birthDate", "1998/06/22")
				.param("residence", "Sevilla")
				.param("nationality", "Camboya")
				.param("number", "32")
				.param("height", "-1.89")
				.param("weight", "80.0")
				.param("dni", "47576890G")
				.param("user.username", "ps98")
				.param("user.password", "secreto"))
			.andExpect(model().attributeHasErrors("pilot"))
			.andExpect(model().attributeHasFieldErrors("pilot", "height"))
			.andExpect(status().isOk())
			.andExpect(view().name("pilots/create"));
	}
	
	//Crear Pilot con valores weight incorrectos
	@WithMockUser(value = "manager1", authorities = "manager")
	@Test
	void testCreateFormPilotHasErrorsWeight() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilots/new",TEST_MANAGER_ID,TEST_TEAM_ID).with(csrf())
				.param("firstName","Pedro")
				.param("lastName", "Sanchez")
				.param("birthDate", "1998/06/22")
				.param("residence", "Sevilla")
				.param("nationality", "Camboya")
				.param("number", "32")
				.param("height", "1.89")
				.param("weight", "150.8")
				.param("dni", "47576890G")
				.param("user.username", "ps98")
				.param("user.password", "secreto"))
			.andExpect(model().attributeHasErrors("pilot"))
			.andExpect(model().attributeHasFieldErrors("pilot", "weight"))
			.andExpect(status().isOk())
			.andExpect(view().name("pilots/create"));
	}
	
	//Crear Pilot con valores weight incorrectos negativos
	@WithMockUser(value = "manager1", authorities = "manager")
	@Test
	void testCreateFormPilotHasErrorsWeightNegative() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilots/new",TEST_MANAGER_ID,TEST_TEAM_ID).with(csrf())
				.param("firstName","Pedro")
				.param("lastName", "Sanchez")
				.param("birthDate", "1998/06/22")
				.param("residence", "Sevilla")
				.param("nationality", "Camboya")
				.param("number", "32")
				.param("height", "1.89")
				.param("weight", "-80.0")
				.param("dni", "47576890G")
				.param("user.username", "ps98")
				.param("user.password", "secreto"))
			.andExpect(model().attributeHasErrors("pilot"))
			.andExpect(model().attributeHasFieldErrors("pilot", "weight"))
			.andExpect(status().isOk())
			.andExpect(view().name("pilots/create"));
	}

	
	//Edit Pilot
	@WithMockUser(value="manager1", authorities={"manager"})
	@Test
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(get("/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/update",TEST_MANAGER_ID,TEST_TEAM_ID,TEST_PILOT_ID))
			.andExpect(status().isOk())
			.andExpect(view().name("/pilots/create"))
			.andExpect(model().attributeExists("pilot"));
	}
	
	// Editar Pilot con valores correctos
	@WithMockUser(value="manager1", authorities={"manager"})
	@Test
	void testProcessUpdateForm() throws Exception {
	mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/update",TEST_MANAGER_ID,TEST_TEAM_ID,TEST_PILOT_ID).with(csrf())
			.param("firstName","Perro")
			.param("lastName", "Sanchez")
			.param("birthDate", "1998/06/22")
			.param("residence", "Sevilla")
			.param("nationality", "Camboya")
			.param("number", "35")
			.param("height", "1.89")
			.param("weight", "80.0")
			.param("dni","47576899G")
			.param("user.username", "ps9809")
			.param("user.password", "secreto234"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/welcome"));
	}
	
	// Editar Pilot con valores dni incorrectos
	@WithMockUser(value="manager1", authorities={"manager"})
	@Test
	void testEditFormPilotHasErrorsDni() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/update",TEST_MANAGER_ID,TEST_TEAM_ID,TEST_PILOT_ID).with(csrf())
				.param("firstName","Perro")
				.param("lastName", "Sanchez")
				.param("birthDate", "1998/06/22")
				.param("residence", "Sevilla")
				.param("nationality", "Camboya")
				.param("number", "17")
				.param("height", "1.89")
				.param("weight", "80.0")
				.param("dni","475768993G")
				.param("user.username", "ps9809")
				.param("user.password", "secreto234"))
			.andExpect(model().attributeHasErrors("pilot"))
			.andExpect(model().attributeHasFieldErrors("pilot", "dni"))
			.andExpect(status().isOk())
			.andExpect(view().name("pilots/create"));
	}
	
	// Editar Pilot con valores birthDate incorrectos
	@WithMockUser(value="manager1", authorities={"manager"})
	@Test
	void testEditFormPilotHasErrorsFirstBirthDate() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/update",TEST_MANAGER_ID,TEST_TEAM_ID,TEST_PILOT_ID).with(csrf())
				.param("firstName","Perro")
				.param("lastName", "Sanchez")
				.param("birthDate", "2020/06/22")
				.param("residence", "Sevilla")
				.param("nationality", "Camboya")
				.param("number", "17")
				.param("height", "1.89")
				.param("weight", "80.0")
				.param("dni","475768993G")
				.param("user.username", "ps9809")
				.param("user.password", "secreto234"))
			.andExpect(model().attributeHasErrors("pilot"))
			.andExpect(model().attributeHasFieldErrors("pilot", "birthDate"))
			.andExpect(status().isOk())
			.andExpect(view().name("pilots/create"));
	}
	
	// Editar Pilot con valores number incorrectos
	@WithMockUser(value="manager1", authorities={"manager"})
	@Test
	void testEditFormPilotHasErrorsNumber() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/update",TEST_MANAGER_ID,TEST_TEAM_ID,TEST_PILOT_ID).with(csrf())
				.param("firstName","Perro")
				.param("lastName", "Sanchez")
				.param("birthDate", "1998/06/22")
				.param("residence", "Sevilla")
				.param("nationality", "Camboya")
				.param("number", "100")
				.param("height", "1.89")
				.param("weight", "80.0")
				.param("dni","47576899G")
				.param("user.username", "ps9809")
				.param("user.password", "secreto234"))
			.andExpect(model().attributeHasErrors("pilot"))
			.andExpect(model().attributeHasFieldErrors("pilot", "number"))
			.andExpect(status().isOk())
			.andExpect(view().name("pilots/create"));
	}
	
	// Editar Pilot con valores number incorrectos negativo
	@WithMockUser(value="manager1", authorities={"manager"})
	@Test
	void testEditFormPilotHasErrorsNumberNegative() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/update",TEST_MANAGER_ID,TEST_TEAM_ID,TEST_PILOT_ID).with(csrf())
				.param("firstName","Perro")
				.param("lastName", "Sanchez")
				.param("birthDate", "1998/06/22")
				.param("residence", "Sevilla")
				.param("nationality", "Camboya")
				.param("number", "-10")
				.param("height", "1.89")
				.param("weight", "80.0")
				.param("dni","47576899G")
				.param("user.username", "ps9809")
				.param("user.password", "secreto234"))
			.andExpect(model().attributeHasErrors("pilot"))
			.andExpect(model().attributeHasFieldErrors("pilot", "number"))
			.andExpect(status().isOk())
			.andExpect(view().name("pilots/create"));
	}
	
	// Editar Pilot con valores height incorrectos
	@WithMockUser(value="manager1", authorities={"manager"})
	@Test
	void testEditFormPilotHasErrorsHeight() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/update",TEST_MANAGER_ID,TEST_TEAM_ID,TEST_PILOT_ID).with(csrf())
				.param("firstName","Perro")
				.param("lastName", "Sanchez")
				.param("birthDate", "1998/06/22")
				.param("residence", "Sevilla")
				.param("nationality", "Camboya")
				.param("number", "32")
				.param("height", "3.0")
				.param("weight", "80.0")
				.param("dni","47576899G")
				.param("user.username", "ps9809")
				.param("user.password", "secreto234"))
			.andExpect(model().attributeHasErrors("pilot"))
			.andExpect(model().attributeHasFieldErrors("pilot", "height"))
			.andExpect(status().isOk())
			.andExpect(view().name("pilots/create"));
	}
	
	// Editar Pilot con valores height incorrectos negative
	@WithMockUser(value="manager1", authorities={"manager"})
	@Test
	void testEditFormPilotHasErrorsHeightNegative() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/update",TEST_MANAGER_ID,TEST_TEAM_ID,TEST_PILOT_ID).with(csrf())
				.param("firstName","Perro")
				.param("lastName", "Sanchez")
				.param("birthDate", "1998/06/22")
				.param("residence", "Sevilla")
				.param("nationality", "Camboya")
				.param("number", "32")
				.param("height", "-1.89")
				.param("weight", "80.0")
				.param("dni","47576899G")
				.param("user.username", "ps9809")
				.param("user.password", "secreto234"))
			.andExpect(model().attributeHasErrors("pilot"))
			.andExpect(model().attributeHasFieldErrors("pilot", "height"))
			.andExpect(status().isOk())
			.andExpect(view().name("pilots/create"));
	}
	
	// Editar Pilot con valores weight incorrectos
	@WithMockUser(value="manager1", authorities={"manager"})
	@Test
	void testEditFormPilotHasErrorsWeight() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/update",TEST_MANAGER_ID,TEST_TEAM_ID,TEST_PILOT_ID).with(csrf())
				.param("firstName","Perro")
				.param("lastName", "Sanchez")
				.param("birthDate", "1998/06/22")
				.param("residence", "Sevilla")
				.param("nationality", "Camboya")
				.param("number", "32")
				.param("height", "1.89")
				.param("weight", "101.0")
				.param("dni","47576899G")
				.param("user.username", "ps9809")
				.param("user.password", "secreto234"))
			.andExpect(model().attributeHasErrors("pilot"))
			.andExpect(model().attributeHasFieldErrors("pilot", "weight"))
			.andExpect(status().isOk())
			.andExpect(view().name("pilots/create"));
	}
	
	// Editar Pilot con valores weight incorrectos negativo
	@WithMockUser(value="manager1", authorities={"manager"})
	@Test
	void testEditFormPilotHasErrorsWeightNegative() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/update",TEST_MANAGER_ID,TEST_TEAM_ID,TEST_PILOT_ID).with(csrf())
				.param("firstName","Perro")
				.param("lastName", "Sanchez")
				.param("birthDate", "1998/06/22")
				.param("residence", "Sevilla")
				.param("nationality", "Camboya")
				.param("number", "32")
				.param("height", "1.89")
				.param("weight", "-1.0")
				.param("dni","47576899G")
				.param("user.username", "ps9809")
				.param("user.password", "secreto234"))
			.andExpect(model().attributeHasErrors("pilot"))
			.andExpect(model().attributeHasFieldErrors("pilot", "weight"))
			.andExpect(status().isOk())
			.andExpect(view().name("pilots/create"));
	}

	//Delete a Pilot
	@WithMockUser(value="manager1", authorities={"manager"})
	@Test
	void testProcessDeleteForm() throws Exception {
		mockMvc.perform(get("/managers/{managerId}/teams/{teamId}/pilots/{pilotId}/remove",TEST_MANAGER_ID,TEST_TEAM_ID,TEST_PILOT_ID))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/welcome"));
}
	
}