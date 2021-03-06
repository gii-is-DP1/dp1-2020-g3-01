package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;

public interface PilotRepository extends CrudRepository<Pilot, Integer>{
	
	@Query("SELECT pilot FROM Pilot pilot WHERE pilot.id =:id")
	public Pilot findById(@Param("id") int id);
	
	@Query("SELECT pilot FROM Pilot pilot WHERE pilot.user.username =:userName")
	public Pilot findByUsername(@Param("userName") String  userName);

	@Modifying
	@Query("DELETE FROM Pilot pilot WHERE pilot.id = :id")
	void remove(@Param("id") Integer Id);
	
	@Query("SELECT pilot FROM Pilot pilot")
	public Collection<Pilot> findAllPilots();
	
}
