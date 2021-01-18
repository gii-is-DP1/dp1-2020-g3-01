package org.springframework.samples.petclinic.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.model.Thread;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ThreadServiceTest {
	
	@Autowired
	protected ForumService forumService;
	@Autowired
	protected TeamService teamService;
	@Autowired
	protected ThreadService threadService;
	@Autowired UserService userService;
	private Forum forum;
	private Team team;
	private Thread thread;

	@Autowired
	EntityManager em;

	@BeforeEach
	void setup(){
		Optional<User> user = this.userService.findUser("manager1");
		User usuario = user.get();
		thread = new Thread();
		List<Message> message = new ArrayList<Message>();
		thread.setCreationDate(Date.from(Instant.now()));
		thread.setTitle("Test");
		thread.setMessages(message);
		thread.setUser(usuario);

	}

	@Test
	@Transactional
	@DisplayName("Find Thread by an id")
	public void shouldFindThreadById() throws DataAccessException {
		Thread thread = this.threadService.findThreadById(1);
		assertThat(thread.getTitle().equals("TITLE"));
	}
	@Test
	@Transactional
	@DisplayName("Find Thread by an id")
	public void shouldFindAllThreads() throws DataAccessException {
		List<Thread> thread = this.threadService.findAll();
		assertThat(thread.size()).isEqualTo(1);
	}
	@Test
	@Transactional
	@DisplayName("Should insert a new Thread")
	public void shouldInsertNewThread() throws DataAccessException {
		this.threadService.saveThread(thread);
		List<Thread> threads = this.threadService.findAll();
		assertThat(threads.size()==2);
	}
	@Test
	@Transactional
	@DisplayName("Should delete Thread")
	public void shouldDeleteThread() throws DataAccessException {
		this.threadService.removeThread(thread.getId());
		List<Thread> threads = this.threadService.findAll();
		assertThat(threads.size()==1);
	}

}
