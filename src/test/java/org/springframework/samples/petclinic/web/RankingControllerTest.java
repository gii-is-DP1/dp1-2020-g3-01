package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Position;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.GrandPrixService;
import org.springframework.samples.petclinic.service.PilotService;
import org.springframework.samples.petclinic.service.PositionService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = RankingController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class RankingControllerTest {

	@MockBean
	private GrandPrixService grandPrixService;

	@MockBean
	private PositionService positionService;

	@MockBean
	private PilotService pilotService;

	@Autowired
	private MockMvc mockMvc;

	private static final int TEST_GRANDPRIX_ID = 6;
	private static final int TEST_GRANDPRIX2_ID = 8;

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
		LocalDate date = LocalDate.now();
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
		
		Pilot pt2 = new Pilot();
		pt2.setUser(user2);
		//LocalDate date = LocalDate.now();
		pt2.setBirthDate(date);
		pt2.setFirstName("Fernando");
		pt2.setLastName("Dumas");
		pt2.setDni("23124568D");
		pt2.setNationality("Russian");
		pt2.setResidence("Moscu");
		pt2.setHeight(1.50);
		pt2.setWeight(80.2);
		pt2.setNumber(11);
		pt2.setId(8);

		Set<Pilot> setP = new HashSet<>();
		setP.add(pt);
		setP.add(pt2);

		Manager m = new Manager();
		m.setUser(user);
		// LocalDate date = LocalDate.now();
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

		Set<Team> setT = new HashSet<>();
		setT.add(team1);

		GrandPrix gp = new GrandPrix();
		gp.setCircuit("Circuit");
		gp.setDistance(123.32);
		gp.setLaps(32);
		gp.setLocation("New Sevilla");
		gp.setId(6);
		Date date2 = new Date();
		gp.setDayOfRace(date2);
		gp.setPilots(setP);
		gp.setTeam(setT);

		Position po = new Position();
		po.setId(20);
		po.setPilot(pt);
		po.setPoint(25);
		po.setPos(1);

		List<Position> posi = new ArrayList<>();
		posi.add(po);

		Set<Position> positions = new HashSet<>();
		GrandPrix gp2 = new GrandPrix();
		gp2.setCircuit("Circuit");
		gp2.setDistance(123.32);
		gp2.setLaps(32);
		gp2.setLocation("New Sevilla");
		gp2.setId(7);
		gp2.setDayOfRace(date2);
		gp2.setPilots(setP);
		gp2.setTeam(setT);
		for(Position pp: posi) {
			
			positions.add(pp);
		}
		gp2.setPositions(positions);
		
		Calendar cal = Calendar.getInstance();
		cal.set(2022, Calendar.JANUARY, 9, 10, 11, 12); //Year, month, day of month, hours, minutes and seconds
		Date date3 = cal.getTime();
		
		GrandPrix gp3 = new GrandPrix();
		gp3.setCircuit("Circuit");
		gp3.setDistance(123.32);
		gp3.setLaps(32);
		gp3.setLocation("New Sevilla");
		gp3.setId(8);
		gp3.setDayOfRace(date3);
		gp3.setPilots(setP);
		gp3.setTeam(setT);
		for(Position pp: posi) {
			
			positions.add(pp);
		}
		gp2.setPositions(positions);

		given(this.grandPrixService.findAllPositionsByGrandPrixId(7)).willReturn(posi);

		given(this.grandPrixService.findAllPilotsByGrandPrixId(6)).willReturn(setP);

		given(this.grandPrixService.findGPById(6)).willReturn(gp);
		
		given(this.grandPrixService.findGPById(8)).willReturn(gp3);

	}

	@WithMockUser(value = "jantontio", authorities = "admin")
	@Test
	public void testShowCreate() throws Exception {
		mockMvc.perform(get("/grandprix/{grandPrixId}/ranking/new", TEST_GRANDPRIX_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("grandprix")).andExpect(view().name("rankings/create"));
	}
	
	@WithMockUser(value = "jantontio", authorities = "admin")
	@Test
	public void testShouldNotShowCreate() throws Exception {
		mockMvc.perform(get("/grandprix/{grandPrixId}/ranking/new", TEST_GRANDPRIX2_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("customMessage")).andExpect(view().name("exception"));
	}

	@WithMockUser(value = "jantontio", authorities = "admin")
	@Test
	public void testListRankings() throws Exception {
		mockMvc.perform(get("/grandprix/{grandPrixId}/ranking/all", TEST_GRANDPRIX_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("grandprix")).andExpect(view().name("rankings/list"));
	}

	@WithMockUser(value = "jantontio", authorities = "admin")
	@Test
	void testCreateRankingFormSuccess() throws Exception {
		List<String> ls = new ArrayList<>();
		Set<Pilot> pilots = this.grandPrixService.findGPById(6).getPilots();
		for (Pilot p : pilots) {
			ls.add(String.valueOf(p.getId()));
		}
		mockMvc.perform(post("/grandprix/{grandPrixId}/ranking/new", TEST_GRANDPRIX_ID).with(csrf())
				.param("circuit", "Circuit").param("dayOfRace", "2021/12/12").param("distance", "100.0")
				.param("id", "9").param("laps", "7").param("location", "Location").param(ls.get(0), "1")
				.param(ls.get(1), "2"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/grandprix/all"));
	}
	
	@WithMockUser(value = "jantontio", authorities = "admin")
	@Test
	void testCreateRankingFormNotSuccess() throws Exception {
		List<String> ls = new ArrayList<>();
		Set<Pilot> pilots = this.grandPrixService.findGPById(6).getPilots();
		for (Pilot p : pilots) {
			System.out.println(p.getFirstName());
			ls.add(String.valueOf(p.getId()));
		}
		mockMvc.perform(post("/grandprix/{grandPrixId}/ranking/new", TEST_GRANDPRIX_ID).with(csrf())
				.param("circuit", "Circuit").param("dayOfRace", "2021/12/12").param("distance", "100.0")
				.param("id", "9").param("laps", "7").param("location", "Location").param(ls.get(0), "1")
				.param(ls.get(1), "1"))
				.andExpect(model().attributeExists("message"))
				.andExpect(status().isOk())
				.andExpect(view().name("rankings/create"));
	}
	
	@WithMockUser(value = "jantontio", authorities = "admin")
	@Test
	void testCreateRankingFormNotSuccessNegativePosition() throws Exception {
		List<String> ls = new ArrayList<>();
		Set<Pilot> pilots = this.grandPrixService.findGPById(6).getPilots();
		for (Pilot p : pilots) {
			System.out.println(p.getFirstName());
			ls.add(String.valueOf(p.getId()));
		}
		mockMvc.perform(post("/grandprix/{grandPrixId}/ranking/new", TEST_GRANDPRIX_ID).with(csrf())
				.param("circuit", "Circuit").param("dayOfRace", "2021/12/12").param("distance", "100.0")
				.param("id", "9").param("laps", "7").param("location", "Location").param(ls.get(0), "-1")
				.param(ls.get(1), "-1"))
				.andExpect(model().attributeExists("message"))
				.andExpect(status().isOk())
				.andExpect(view().name("rankings/create"));
	}
	
	@WithMockUser(value = "jantontio", authorities = "admin")
	@Test
	void testCreateRankingFormNotSuccessTooHighPosition() throws Exception {
		List<String> ls = new ArrayList<>();
		Set<Pilot> pilots = this.grandPrixService.findGPById(6).getPilots();
		for (Pilot p : pilots) {
			System.out.println(p.getFirstName());
			ls.add(String.valueOf(p.getId()));
		}
		mockMvc.perform(post("/grandprix/{grandPrixId}/ranking/new", TEST_GRANDPRIX_ID).with(csrf())
				.param("circuit", "Circuit").param("dayOfRace", "2021/12/12").param("distance", "100.0")
				.param("id", "9").param("laps", "7").param("location", "Location").param(ls.get(0), "21")
				.param(ls.get(1), "21"))
				.andExpect(model().attributeExists("message"))
				.andExpect(status().isOk())
				.andExpect(view().name("rankings/create"));
	}
	
	@WithMockUser(value = "jantontio", authorities = "admin")
	@Test
	void testCreateRankingFormNotSuccessNullPosition() throws Exception {
		List<String> ls = new ArrayList<>();
		Set<Pilot> pilots = this.grandPrixService.findGPById(6).getPilots();
		for (Pilot p : pilots) {
			System.out.println(p.getFirstName());
			ls.add(String.valueOf(p.getId()));
		}
		mockMvc.perform(post("/grandprix/{grandPrixId}/ranking/new", TEST_GRANDPRIX_ID).with(csrf())
				.param("circuit", "Circuit").param("dayOfRace", "2021/12/12").param("distance", "100.0")
				.param("id", "9").param("laps", "7").param("location", "Location"))
				.andExpect(model().attributeExists("message"))
				.andExpect(status().isOk())
				.andExpect(view().name("rankings/create"));
	}

}