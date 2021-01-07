package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.GrandPrix;

public interface GrandPrixRepository extends CrudRepository<GrandPrix, Integer> {
	
	@Query("SELECT gp FROM GrandPrix gp")
	public Collection<GrandPrix> findAll();
	
}

