package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Mechanic;

public interface MechanicRepository extends Repository<Mechanic, Integer>{
	
	@Query("SELECT mechanic FROM Mechanic mechanic WHERE mechanic.id =:id")
	public Mechanic findById(@Param("id") int id);

	@Query("SELECT mechanic FROM Mechanic mechanic WHERE mechanic.user.username =:userName")
	public Mechanic findByUserName(@Param("userName") String  userName);

	void save(Mechanic Mechanic) throws DataAccessException;
	
	@Query("SELECT  mechanic FROM Mechanic mechanic")
	public Collection<Mechanic> findAll();
}
