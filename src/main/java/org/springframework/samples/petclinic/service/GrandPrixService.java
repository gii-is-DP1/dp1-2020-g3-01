package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.repository.GrandPrixRepository;
import org.springframework.samples.petclinic.repository.MotorcycleRepository;
import org.springframework.samples.petclinic.service.exceptions.MaxTeamsException;
import org.springframework.samples.petclinic.service.exceptions.NoPilotsException;
import org.springframework.samples.petclinic.service.exceptions.PilotWithoutBikeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GrandPrixService {

	private GrandPrixRepository grandPrixRepository;
	private MotorcycleRepository motorcycleRepository;

	@Autowired
	public GrandPrixService(GrandPrixRepository grandPrixRepository, MotorcycleRepository motorcycleRepository) {
		this.grandPrixRepository = grandPrixRepository;
		this.motorcycleRepository = motorcycleRepository;
	}

	@Transactional
	public void saveGP(GrandPrix grandPrix, Team team) throws DataAccessException, NoPilotsException, PilotWithoutBikeException, MaxTeamsException {
		if(grandPrix.getTeam().size()>10) {
			throw new MaxTeamsException();
		} else if (team.getPilot().size()==0) {
			throw new NoPilotsException();
		} else if(team.getPilot().stream().anyMatch(p -> motorcycleRepository.findMotorcycleByPilotId(p.getId()).equals(null))) {
			throw new PilotWithoutBikeException();
		} else {
			grandPrixRepository.save(grandPrix);
		}
	}
	
	@Transactional
	public Collection<GrandPrix> findAll() throws DataAccessException {
		return grandPrixRepository.findAll();
	}
	
	@Transactional
	public GrandPrix findGPById(int id) throws DataAccessException {
		return grandPrixRepository.findGPById(id);
	}
}

