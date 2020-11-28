package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class PilotControllerTests {
	
	@Autowired
	private PilotController pilotController;
	
	@MockBean
	private PilotService pilotService;
	
	@MockBean
	private ManagerService managerService;
	
	@MockBean
	private TeamService teamService;
	
	@MockBean
	private AuthoritiesService authoritiesService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private static final int TEST_TEAM_ID = 1;
	private Team team1;
	
	private static final int TEST_MANAGER_ID = 1;
	private Manager manager1;
	
	private Pilot pilot1;
	
	@BeforeEach
	void setUp() throws Exception{
		
//		pilot1 = new Pilot();
//		pilot1.setId(1);
//		pilot1.setFirstName("Juan");
//		pilot1.setLastName("Garcia");
//		pilot1.setNationality("España");
//		pilot1.setResidence("Malaga");
//		pilot1.setDni("12345678X");
//		pilot1.setHeight(1.75);
//		pilot1.setWeight(70.0);
//		LocalDate fecha = LocalDate.of(1980, 6, 15);
//		pilot1.setBirthDate(fecha);
//		pilot1.setNumber(31);
//		given(this.teamService.findTeamById(TEST_TEAM_ID).getPilot().stream().filter(p -> p.equals(pilot1)).findFirst().get()).willReturn(pilot1);
//		
	}
	
	@WithMockUser(value = "manager1")
	@Test
	void testInitCreationForm() throws Exception{
		mockMvc.perform(get("managers/{managerId}/teams/{teamId}/pilots/new", TEST_MANAGER_ID, TEST_TEAM_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("pilot"))
		.andExpect(view().name("pilots/create"));
	}
	
	@WithMockUser(value = "manager1")
	@Test
	void testProcessCreationForm() throws Exception{
		mockMvc.perform(post("managers/{managerId}/teams/{teamId}/pilots/new", TEST_MANAGER_ID, TEST_TEAM_ID)
		.with(csrf())
		.param("firstName", "Sonic")
		.param("lastName", "The Hedgehog")
		.param("birthDate", "1991/06/23")
		.param("residence", "Green Hills")
		.param("nationality", "Japon")
		.param("number", "100")
		.param("height", "1.55")
		.param("weight", "35.0"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/welcome"));
	}
	
	@WithMockUser(value ="manager1")
	@Test
	void testProcessCreationFormHasErrors() throws Exception{
		mockMvc.perform(post("managers/{managerId}/teams/{teamId}/pilots/new", TEST_MANAGER_ID, TEST_TEAM_ID)
		.with(csrf())
		.param("firstName", "Sonik")
		.param("lastName", "The Hedgehog")
		.param("birthDate", "1991/06/23")
		.param("residence", "Green Hills")
		.param("nationality", "Japon")
		.param("number", "100")
		.param("height", "2.0")
		.param("weight", "35.0"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("pilot"))
		.andExpect(model().attributeHasFieldErrors("pilot", "firstName"))
		.andExpect(model().attributeHasFieldErrors("pilot", "height"))
		.andExpect(view().name("pilots/create"));
	}

}
