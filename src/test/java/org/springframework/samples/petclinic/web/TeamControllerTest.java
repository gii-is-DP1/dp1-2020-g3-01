package org.springframework.samples.petclinic.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.MechanicService;
import org.springframework.samples.petclinic.service.MechanicServiceTest;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=TeamController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class TeamControllerTest {
	
	private static final int TEST_TEAM_ID = 1;
	
	@Autowired
	private TeamController teamController;

	@MockBean
	private TeamService teamService;
	
	@MockBean
	private ManagerService managerService;
	
	@MockBean
	private MechanicService mechanicService;

	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {

//		given(this.clinicService.findVets()).willReturn(Lists.newArrayList(james, helen));
	}
	
	//teams/1/mechanics/new
	@WithMockUser(value = "spring")
	@Test
	void testGetNewMechanic() throws Exception {
		mockMvc.perform(get("/teams/1/mechanics/new"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("mechanic"))
		.andExpect(view().name("mechanics/createOrUpdateMechanicForm"));
	}
	
	// Create Tournament Positive case valid inputs
//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessTournamentFormSuccess() throws Exception {		
//		mockMvc.perform(post("/teams/{teamId}/mechanics/new",TEST_TEAM_ID).with(csrf())
//				.param("firstName", "Penelope")
//				.param("lastName", "Cruz")
//				.param("birthDay", "1966/1/1")				
//				.param("residence","Spain")
//				.param("nationality", "Spain")
//				.param("dni", "12345678Z")
//				.param("type", "ENGINE")
//				.param("user.username", "hdfaskldhfklsdfj")
//				.param("user.password", "hdfaskldhfklsdfjsadasda"))
//				 //.andExpect(model().attributeHasNoErrors("tournament"))
//				.andExpect(status().is2xxSuccessful())
//				.andExpect(view().name("redirect:/welcome"));
//	}
	

	
	

}
