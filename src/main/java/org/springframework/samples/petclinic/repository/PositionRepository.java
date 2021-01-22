package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Position;

public interface PositionRepository extends CrudRepository<Position, Integer>{
	
	@Query("SELECT p FROM Position p")
	public Collection<Position> findAll();
	

}
