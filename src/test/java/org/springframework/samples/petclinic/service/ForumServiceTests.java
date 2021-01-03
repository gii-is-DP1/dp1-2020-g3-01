package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.security.Provider.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Forum;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.petclinic.model.Thread;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ForumServiceTests {
	
	@Autowired
	private ForumService forumService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private ThreadService threadService;
	
	private Forum forum;
	private Team team;
	private List<Thread> threads;
	
	@Autowired
	EntityManager em;
	
	@BeforeEach
	void setUp() {
		forum = this.forumService.findForumById(1);
		team = this.teamService.findTeamById(1);
		threads = this.threadService.findThreadsByForumId(1).stream().collect(Collectors.toList());
	}
	
	//CASOS POSITIVOS
	
	//Crear un foro correctamente
	
	@Test
	@Transactional
	@DisplayName("Create a forum correctly")
	void shouldCreateNewForum() throws DataAccessException {
		
		forum = new Forum();
		
		forum.setName("Test");
		forum.setCreationDate(new Date());
		forum.setThreads(threads);
		forum.setTeam(team);
		
		this.forumService.saveForum(forum);
		assertThat(this.forumService.countForums(team.getId()).equals(1));
		
	}
	
	@Test
	@Transactional
	@DisplayName("Find Forum by TeamId")
	void shouldFindForum() throws DataAccessException {
		Forum forum2 = this.forumService.findForumByTeamId(team.getId());
		assertThat(forum.equals(forum2));
	}
	
	@Test
	@Transactional
	@DisplayName("Modify a forum correctly")
	void shouldModifyForum() throws DataAccessException {
		forum.setName("Prueba");
		this.forumService.saveForum(forum);
		assertThat(this.forumService.findForumById(1).equals(forum));
	}

}
