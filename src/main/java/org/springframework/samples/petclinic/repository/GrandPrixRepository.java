package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.Team;

public interface GrandPrixRepository extends CrudRepository<GrandPrix, Integer> {
	
	@Query("SELECT gp FROM GrandPrix gp")
	public Collection<GrandPrix> findAll();
	
	
	@Query("SELECT gp FROM GrandPrix gp WHERE gp.id = :id")
	GrandPrix findGPById(@Param("id") int id);
	
	@Query("SELECT team FROM GrandPrix gp WHERE gp.id = :id")
	Collection<Team> findTeamsOfGP(@Param("id") int id);
}

