package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Position;

public interface GrandPrixRepository extends CrudRepository<GrandPrix, Integer> {
	
	@Query("SELECT gp FROM GrandPrix gp")
	public Collection<GrandPrix> findAll();
	
	
	@Query("SELECT gp FROM GrandPrix gp WHERE gp.id = :id")
	GrandPrix findGPById(@Param("id") int id);

	@Query("SELECT pilots FROM GrandPrix gp WHERE gp.id = :id")
	Set<Pilot> findAllPilots(@Param("id") int id);
	
	@Query("SELECT positions FROM GrandPrix gp WHERE gp.id = :id")
	Set<Position> findAllPositions(@Param("id") int id);
	
}

