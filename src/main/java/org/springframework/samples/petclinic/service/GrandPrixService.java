package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.repository.GrandPrixRepository;
import org.springframework.stereotype.Service;

@Service
public class GrandPrixService {
	
	private GrandPrixRepository grandPrixRepository;
	
	@Autowired
	public GrandPrixService(GrandPrixRepository grandPrixRepository) {
		this.grandPrixRepository = grandPrixRepository;
	}
	
	@Autowired
	public void saveGrandPrix(GrandPrix grandPrix) throws DataAccessException {
		grandPrixRepository.save(grandPrix);
	}
	
	@Autowired
	public GrandPrix findById(int id) throws DataAccessException {
		return grandPrixRepository.findById(id);
	}
	
	@Autowired
	public void remove(int id) throws DataAccessException {
		grandPrixRepository.remove(id);
	}

}
