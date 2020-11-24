package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class TeamServiceTests {
	@Autowired
	protected TeamService teamService;

	@Autowired
	protected ManagerService managerService;
	
	
	@Test
	void shouldFindTeam() {
		Team team1 = this.teamService.findManager(1);
		assertThat(team1.getName()).startsWith("Las Divinas");
		assertThat(team1.getManager().getFirstName()).isEqualTo("Herrera");
	}
	

//	@Test
//	@Transactional
//	public void shouldInsertTeamIntoDatabaseAndGenerateId() {
//		Manager manager1 = this.managerService.findManagerById(1);
//		
//
//		Team team = new Team();
//		team.setName("El Giraldillo");
//		Date dateTeam = new Date();
//		team.setCreationDate(dateTeam);
//		team.setNif("34234543L");
//		assertThat(manager1.);
//
//            try {
//                this.petService.savePet(pet);
//            } catch (DuplicatedPetNameException ex) {
//                Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, ex);
//            }
//		this.ownerService.saveOwner(owner6);
//
//		owner6 = this.ownerService.findOwnerById(6);
//		assertThat(owner6.getPets().size()).isEqualTo(found + 1);
//		// checks that id has been generated
//		assertThat(pet.getId()).isNotNull();
//	}
//	
	
	
	
}
