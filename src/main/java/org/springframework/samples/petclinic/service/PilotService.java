package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.repository.PilotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PilotService {
	
	private PilotRepository pilotRepository;
	
	@Autowired
	public PilotService(PilotRepository pilotRepository) {
		this.pilotRepository = pilotRepository;
	}
	
	@Transactional
	public void savePilot(Pilot pilot) throws DataAccessException {
		pilotRepository.save(pilot);
	}
	
	@Transactional
	public Pilot findById(Integer id) throws DataAccessException {
		return pilotRepository.findById(id).get();
	}
	
	@Transactional
	public Pilot findByUsername(String username) throws DataAccessException {
		return pilotRepository.findByUsername(username);
	}

}
