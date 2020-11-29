package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Motorcycle;

public interface MotorcycleRepository extends CrudRepository<Motorcycle, Integer> {
	
	@Query("SELECT COUNT(*) FROM Motorcycle motorcycle WHERE motorcycle.pilot.id = :id")
	Integer countBikes(@Param("id") int id);

	@Query("SELECT motorcycle FROM Motorcycle motorcycle WHERE motorcycle.id = :id")
	Motorcycle findMotorcycleById(@Param("id") int id);
	
	@Query("SELECT ALL motorcycle FROM Motorcycle motorcycle")
	Collection<Motorcycle> findAll();
  
  	@Modifying
	@Query("DELETE FROM Motorcycle motorcycle WHERE motorcycle.id = :id")
	void remove(@Param("id") Integer Id);
	
}

