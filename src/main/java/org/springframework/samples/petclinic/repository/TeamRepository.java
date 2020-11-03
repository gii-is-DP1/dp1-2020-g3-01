package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.stereotype.Repository;


public interface TeamRepository extends CrudRepository<Team, Integer>{

	@Query("SELECT COUNT(*) FROM Team team WHERE team.manager.id = :id")
	Integer countTeams(@Param("id") int id);
	
}
