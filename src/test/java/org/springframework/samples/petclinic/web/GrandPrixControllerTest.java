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
import org.springframework.samples.petclinic.model.Authorities;
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
		

	}

	// Historia de usuario 16 - Listado de todos los gran premios

	@WithMockUser(value = "admin1", authorities = "admin")
	@Test
	void testListAllGP() throws Exception {
		mockMvc.perform(get("/grandprix/all")).andExpect(status().isOk())
				.andExpect(model().attributeExists("grandPrix")).andExpect(view().name("grandprix/list"));
	}

}