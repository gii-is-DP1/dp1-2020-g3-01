package org.springframework.samples.petclinic.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.MechanicService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = MechanicController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
classes = WebSecurityConfigurer.class),
excludeAutoConfiguration = SecurityConfiguration.class)
public class MechanicControllerTest {
	
	@MockBean
	private MechanicService mechanicService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;
	
	private static final int TEST_MANAGER_ID = 1;
	private static final int TEST_TEAM_ID = 1;
	private static final int TEST_MECHANIC_ID = 1;
	
	
	@BeforeEach
	void setUp() throws Exception {
		
		
	}
	
	
	
	@WithMockUser(value = "spring")
	@Test
	void testGetNewTeam() throws Exception {
		mockMvc.perform(get("/managers/{managerId}/teams/{teamId}/mechanics/{mechanicId}/details", TEST_MANAGER_ID,TEST_TEAM_ID,TEST_MECHANIC_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("mechanic")).
		andExpect(view().name("mechanics/mechanicDetails"));
	}
	

}
