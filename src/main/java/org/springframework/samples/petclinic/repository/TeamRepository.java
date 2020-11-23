package org.springframework.samples.petclinic.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.samples.petclinic.model.Team;


public interface TeamRepository extends CrudRepository<Team, Integer>{

	@Query("SELECT COUNT(*) FROM Team team WHERE team.manager.id = :id")
	Integer countTeams(@Param("id") int id);

	@Query("SELECT mechanic FROM Team team WHERE team.id = :id")
	Set<Mechanic> getMechanicsById(@Param("id") int id);

	@Query("SELECT team FROM Team team WHERE team.id = :id")
	Team getTeamById(@Param("id") int id);
	
}
