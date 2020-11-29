package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Owner;

public interface ManagerRepository extends Repository<Manager, Integer>{
	
	@Query("SELECT manager FROM Manager manager WHERE manager.id =:id")
	public Manager findById(@Param("id") int id);

	@Query("SELECT manager FROM Manager manager WHERE manager.user.username =:userName")
	public Manager findByUserName(@Param("userName") String  userName);

	void save(Manager manager) throws DataAccessException;
}
