package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.GrandPrix;
import org.springframework.samples.petclinic.model.Pilot;

import org.springframework.samples.petclinic.model.Team;

import org.springframework.samples.petclinic.model.Position;

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

	//CUIDADO, ESTE METODO SOLO DEBE UTILIZARSE PARA INSCRIBIR O ELIMINAR EQUIPOS, PARA HACER SAVE NORMAL ES EL DE ABAJO
	
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
	
	//SAVE NORMAL
	
	@Transactional
	public void save(GrandPrix gp) throws DataAccessException {
		grandPrixRepository.save(gp);
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
	public List<Position> findAllPositionsByGrandPrixId(int id) throws DataAccessException {
		Set<Position> result = grandPrixRepository.findAllPositions(id);
		List<Position> positionSorted = new ArrayList<>();
		for (Position p : result) {
			positionSorted.add(p);
		}

		Collections.sort(positionSorted, (o1, o2) -> o1.getPos().compareTo(o2.getPos()));
		return positionSorted;
	}
	
	@Transactional
	public GrandPrix findGPById(int id) throws DataAccessException {
		return grandPrixRepository.findGPById(id);
	}
	
	@Transactional
	public void removeGP(GrandPrix gp) throws DataAccessException {
		grandPrixRepository.delete(gp);
	}
	
	@Transactional
	public Collection<Team> findTeamsOfGP(int id) throws DataAccessException {
		return grandPrixRepository.findTeamsOfGP(id);
	}
}
