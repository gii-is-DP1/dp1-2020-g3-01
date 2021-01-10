package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.repository.PilotRepository;
import org.springframework.samples.petclinic.repository.TeamRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.service.exceptions.TwoMaxPilotPerTeamException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PilotService {
	
	private PilotRepository pilotRepository;
	private TeamRepository teamRepository;
	
	@Autowired
	public PilotService(PilotRepository pilotRepository, TeamRepository teamRepository) {
		this.pilotRepository = pilotRepository;
		this.teamRepository = teamRepository;
	}
	
	@Transactional(rollbackFor = TwoMaxPilotPerTeamException.class)
	public void savePilot(Pilot pilot, Team team) throws DataAccessException, TwoMaxPilotPerTeamException {
		if(pilot.getId()!=null) {
			pilotRepository.save(pilot);
		} else if(teamRepository.getPilotsById(team.getId()).size()>2) {
			throw new TwoMaxPilotPerTeamException();
		} else {
			pilotRepository.save(pilot);
		}
		
	}
	
	@Transactional
	public Pilot findById(Integer id) throws DataAccessException {
		return pilotRepository.findById(id).get();
	}
	
	@Transactional
	public Pilot findByUsername(String username) throws DataAccessException {
		return pilotRepository.findByUsername(username);
	}
	@Transactional
	public void removePilot(Integer id) throws DataAccessException{
		pilotRepository.remove(id);
	}
	@Transactional
	public Collection<Pilot> findAllPilots() throws DataAccessException{
		return pilotRepository.findAllPilots();
	}
	
}
