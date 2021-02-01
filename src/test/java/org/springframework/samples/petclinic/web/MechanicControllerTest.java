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
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.Type;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.MechanicService;
import org.springframework.samples.petclinic.service.TeamService;
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
	
	private static final int TEST_MANAGER_ID = 6;
	private static final int TEST_TEAM_ID = 3;
	private static final int TEST_MECHANIC_ID = 7;
	
	
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

		given(this.mechanicService.findMechanicById(TEST_MECHANIC_ID)).willReturn(mc);
	}
	
	
	
	@WithMockUser(value = "jantontio", authorities = "manager")
	@Test
	void testShowMechanic() throws Exception {
		mockMvc.perform(get("/managers/{managerId}/teams/{teamId}/mechanics/{mechanicId}/details", TEST_MANAGER_ID,TEST_TEAM_ID,TEST_MECHANIC_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("mechanic")).
		andExpect(view().name("mechanics/mechanicDetails"));
	}
	

}
