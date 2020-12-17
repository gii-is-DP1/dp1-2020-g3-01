package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.GrandPrix;

public interface GrandPrixRepository extends CrudRepository<GrandPrix, Integer> {
	
	@Query("SELECT grandPrix FROM GrandPrix grandPrix WHERE grandPrix.id =: id")
	public GrandPrix findById(@Param("id") int id);
	
	@Modifying
	@Query("DELETE FROM GrandPrix grandPrix WHERE grandPrix.id =: id")
	public void remove(@Param("id") int id);

}
