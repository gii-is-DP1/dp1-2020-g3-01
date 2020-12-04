package org.springframework.samples.petclinic.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ManagerService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.TeamService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

class TeamControllerTest {

	@Autowired
	private TeamController teamController;

	@MockBean
	private TeamService teamService;

	@MockBean
	private ManagerService managerService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	private Team team1;

	private static final int TEST_MANAGER_ID = 1;
	private Manager manager1;

	@BeforeEach
	void setUp() throws Exception {

//		team1 = new Team();
//		team1.setId(1);
//		Date fecha = new Date();
//		team1.setCreationDate(fecha);
//		team1.setName("Ducati");
//		team1.setManager(manager1);
//		team1.setNif("12345678X");
//		given(this.teamService.findManager(TEST_MANAGER_ID)).willReturn(team1);

	}

	@Disabled
	@WithMockUser(value = "manager1")
	@Test
	void testInitEditTeam() throws Exception {
		mockMvc.perform(get("/managers/{managerId}/teams/edit", TEST_MANAGER_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("team"))
		.andExpect(view().name("teams/createOrUpdateTeamForm"));
	}

	@Disabled
	@WithMockUser(value = "manager1")
	@Test
	void testEditTeamSuccess() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/edit", TEST_MANAGER_ID).with(csrf())
			.param("name", "Las Divinas")
			.param("creationDate", "1960-01-01 12:40:01")
			.param("nif", "12345678D")
			.param("manager", "1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/managers/details"));
	}

	@Disabled
	@WithMockUser(value = "manager1")
	@Test
	void testEditTeamFormHasErrors() throws Exception {
		mockMvc.perform(post("/managers/{managerId}/teams/edit", TEST_MANAGER_ID)
				.with(csrf())
				.param("name", "Las Divinas 2")
				.param("creationDate", "1960-01-01 12:40:01")
				.param("nif", "12345678D")
				.param("manager", "1"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("team"))
				.andExpect(model().attributeHasFieldErrors("team", "name"))
				.andExpect(model().attributeHasFieldErrors("team", "nif"))
				.andExpect(view().name("teams/createOrUpdateTeamForm"));
	}

}
