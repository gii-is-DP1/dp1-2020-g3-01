package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.Motorcycle;

public interface GrandPrixRepository extends CrudRepository<GrandPrix, Integer> {
	
	
	
}

