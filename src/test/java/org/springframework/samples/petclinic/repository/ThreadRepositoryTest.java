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
import org.springframework.samples.petclinic.model.Thread;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ThreadRepositoryTest {

	
	@Autowired
	ThreadRepository threadRepository;
	
	@Test
	public void shouldFindThreadById() throws Exception {

		Thread hilo = this.threadRepository.findThreadById(1);

		assertThat(hilo.getTitle()).isEqualTo("Como solucionar una fuga de aceite");
	}
	
	@Test
	public void shouldFindAllThreads() throws Exception {

		List<Thread> hilos = this.threadRepository.finAll();

		assertThat(hilos.size()).isEqualTo(1);
	}
	
	
	
	
}
