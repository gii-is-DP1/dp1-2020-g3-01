package org.springframework.samples.petclinic.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Forum;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ForumRepositoryTest {

	
	@Autowired
	ForumRepository forumRepository;
	
	@Test
	public void shouldFindForumById() throws Exception {

		Forum foro = this.forumRepository.findForumById(1);

		assertThat(foro.getName()).isEqualTo("FORUM");
	}
	
	@Test
	public void shouldFindForumByTeamId() throws Exception {

		Forum foro = this.forumRepository.findForumByTeamId(1);

		assertThat(foro.getName()).isEqualTo("FORUM");
	}
	
	@Test
	public void shouldFindAllForum() throws Exception {

		List<Forum> foros = this.forumRepository.findAll();

		assertThat(foros.size()).isEqualTo(1);
	}
	
	
	
}
