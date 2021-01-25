package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Forum;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.Thread;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ForumServiceTest {

	@Autowired
	protected ForumService forumService;
	@Autowired
	protected TeamService teamService;

	private Forum forum;
	private Team team;

	@Autowired
	EntityManager em;

	@BeforeEach
	void setup(){

		forum = new Forum();
		team = this.teamService.findTeamById(1);
		List<Thread> threads = new ArrayList<Thread>();
		forum.setCreationDate(Date.from(Instant.now()));
		forum.setName("Test");
		forum.setTeam(team);
		forum.setThreads(threads);

	}

	@Test
	@Transactional
	@DisplayName("Find Forum by an id")
	public void shouldFindForumById() throws DataAccessException {
		Forum forum = this.forumService.findForumById(1);
		assertThat(forum.getName().equals("FORUM"));
	}

	@Test
	@Transactional
	@DisplayName("Find Forum by its Team Id")
	public void shouldFindForumByTeam() throws DataAccessException {
		Forum forum = this.forumService.findForumByTeamId(1);
		assertThat(forum.getName().equals("Test"));
	}

	@Test
	@Transactional
	@DisplayName("Should insert a new Forum")
	public void shouldInsertNewForum() throws DataAccessException {
		this.forumService.saveForum(forum);
		List<Forum> forums = this.forumService.findAll();
		assertThat(forums.size()==2);
	}

	@Test
	@Transactional
	@DisplayName("Should remove a Forum")
	public void shouldRemoveForum() throws DataAccessException {
		this.forumService.removeForum(forum.getId());
		List<Forum> forums = this.forumService.findAll();
		assertThat(forums.size()==1);
	}

	@Test
	@Transactional
	@DisplayName("Shouldn't insert a forum with empty name")
	public void shouldNotInsertForumEName() throws DataAccessException {
		this.forum.setName("");
		List<Forum> forums = this.forumService.findAll();
		assertThat(forums.size()==1);
		assertThrows(ConstraintViolationException.class,() ->{forumService.saveForum(forum); em.flush();});
	}

	@Test
	@Transactional
	@DisplayName("Shouldn't insert a forum with wrong name")
	public void shouldNotInsertForumWName() throws DataAccessException {
		this.forum.setName("T");
		List<Forum> forums = this.forumService.findAll();
		assertThat(forums.size()==1);
		assertThrows(ConstraintViolationException.class,() ->{forumService.saveForum(forum); em.flush();});
	}



}