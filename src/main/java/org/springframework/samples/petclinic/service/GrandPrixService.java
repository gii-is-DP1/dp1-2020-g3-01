package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Position;
import org.springframework.samples.petclinic.repository.GrandPrixRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional
	public Collection<GrandPrix> findAll() throws DataAccessException {
		return grandPrixRepository.findAll();
	}
	
	@Transactional
	public Set<Pilot> findAllPilotsByGrandPrixId(int id) throws DataAccessException {
		return grandPrixRepository.findAllPilots(id);
	}
	
	@Transactional
	public Set<Position> findAllPositionsByGrandPrixId(int id) throws DataAccessException {
		return grandPrixRepository.findAllPositions(id);
	}
	
	@Transactional
	public GrandPrix findGPById(int id) throws DataAccessException {
		return grandPrixRepository.findGPById(id);
	}
	
	@Transactional
	public void removeGP(GrandPrix gp) throws DataAccessException {
		this.grandPrixRepository.delete(gp);
	}
}

