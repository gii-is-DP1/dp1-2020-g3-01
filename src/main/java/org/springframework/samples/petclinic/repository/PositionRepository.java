package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Position;

public interface PositionRepository extends CrudRepository<Position, Integer> {
	
	@Query("SELECT position FROM Position position WHERE position.id =: id")
	public Position findById(@Param("id") int id);
	
	@Query("DELETE FROM Position position WHERE position.id =: id")
	public void remove(@Param("id") int id);

}
