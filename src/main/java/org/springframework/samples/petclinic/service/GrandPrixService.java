package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.Motorcycle;
import org.springframework.samples.petclinic.repository.GrandPrixRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.CrudRepository;

@Service
public class GrandPrixService {

	private GrandPrixRepository grandPrixRepository;

	@Autowired
	public GrandPrixService(GrandPrixRepository grandPrixRepository) {
		this.grandPrixRepository = grandPrixRepository;
	}

	
	@Transactional
	public void saveGP(GrandPrix grandPrix) throws DataAccessException {
		grandPrixRepository.save(grandPrix);
	}
}

