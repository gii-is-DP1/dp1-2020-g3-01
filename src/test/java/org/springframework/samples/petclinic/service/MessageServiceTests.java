package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.Date;
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
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class MessageServiceTests {

	@Autowired
	MessageService messageService;

	@Autowired
	UserService userService;

	private Message message;
	
	@Autowired
	EntityManager em;

	@BeforeEach
	void setUp() {
		message = new Message();
		Optional<User> user = this.userService.findUser("manager1");
		User usuario = user.get();
		message.setId(1);
		message.setText("Esto es el primer mensaje.");
		message.setUser(usuario);
		message.setCreationDate(new Date());
	}
	
	@Test
	@Transactional
	@DisplayName("Finding Message By Id")
	void shouldFindTeamById() throws DataAccessException {

		Message m = this.messageService.findMessageById(1);
				
		assertThat(m.getId()).isEqualTo(1);
	}

	// CASOS POSITIVOS

	// Insertar mensaje con todos los campos correctos

	@Test
	@Transactional
	@DisplayName("Inserting message correctly")
	void shouldCreateMessageCorrectly() throws DataAccessException {
		Optional<User> user = this.userService.findUser("manager2");
		User usuario = user.get();

		Message mensajeNuevo = new Message();

		String content = "Esto es un mensaje de prueba";
		String title = "Titulo";
		Date date = new Date();

		mensajeNuevo.setId(2);
		mensajeNuevo.setText(content);
		mensajeNuevo.setCreationDate(date);
		mensajeNuevo.setUser(usuario);
		mensajeNuevo.setTitle(title);
		System.out.println("el mensaje"+mensajeNuevo.getText());
		
		this.messageService.saveMessage(mensajeNuevo);
		
		Message men = this.messageService.findMessageById(2);		
		assertThat(men.getText()).isEqualTo("Esto es un mensaje de prueba");
	}

	
	// Editar mensaje con valores correctos

	@Test
	@DisplayName("Edit team correctly")
	@Transactional
	void shouldEditMessageCorrectly() throws Exception {

		message.setText("Este es un mensaje editado");

		this.messageService.saveMessage(message);

		assertThat(message.getText()).isEqualTo("Este es un mensaje editado");
	}

	// CASOS NEGATIVOS

	// Editar mensaje con contenido muy corto

	@Test
	@DisplayName("Edit message with incorrect text")
	@Transactional
	void shouldThrowExceptionEditShortMessage() throws Exception {

		message.setText("hol");

		assertThrows(ConstraintViolationException.class, () -> {
			this.messageService.saveMessage(message);
			em.flush();
		});
	}
	
	// Eliminar un message
	
	@Test
	@Transactional
	@DisplayName("Delete Message")
	void shouldDeleteMessage() throws DataAccessException {
		this.messageService.removeMessage(1);
		Message m = this.messageService.findMessageById(1);
		assertThat(m).isEqualTo(null);
	}
	
	
	
}
