package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Pilot;

public interface PilotRepository extends CrudRepository<Pilot, Integer>{
	
	@Query("SELECT pilot FROM Pilot pilot WHERE pilot.id =:id")
	public Pilot findById(@Param("id") int id);
	
	@Query("SELECT pilot FROM Pilot pilot WHERE pilot.user.username =:userName")
	public Pilot findByUsername(@Param("userName") String  userName);
	
	@Query("SELECT pilot FROM Pilot pilot")
	public Collection<Pilot> findAllPilots();

}
