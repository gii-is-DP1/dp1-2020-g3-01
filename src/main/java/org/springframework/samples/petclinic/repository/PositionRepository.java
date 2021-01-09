package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Position;

public interface PositionRepository extends CrudRepository<Position, Integer>{

}
