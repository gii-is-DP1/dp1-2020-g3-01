package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Set;

import org.hibernate.exception.ConstraintViolationException;

import org.ehcache.shadow.org.terracotta.offheapstore.util.FindbugsSuppressWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.repository.TeamRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedTeamNIF;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedTeamName;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class TeamService {

	private TeamRepository teamRepository;

	@Autowired
	public TeamService(TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}

	@Transactional
	public void saveTeam(Team team) throws DataIntegrityViolationException{

		teamRepository.save(team);


	}

	@Transactional
	public Integer countTeams(Integer id) throws DataAccessException {
		return teamRepository.countTeams(id);
	}


	@Transactional
	public Collection<String> findAllTeamsNames() throws DataAccessException {
		return teamRepository.findAllTeamsNames();
	}

	@Transactional
	public Collection<String> findAllTeamsNIF() throws DataAccessException {
		return teamRepository.findAllTeamsNIF();
	}

	@Transactional(readOnly = true)
	public Team findManager(int id) throws DataAccessException {
		return teamRepository.findManager(id);
	}
	
	@Transactional(readOnly = true)
	public Set<Pilot> findPilotsByTeamId(int id) throws DataAccessException {
		return teamRepository.findPilotsByTeamId(id);
	}	

	@Transactional
	public void removeTeam(Team team) throws DataAccessException {
		//Team team = this.teamRepository.findTeamById(id);
		teamRepository.delete(team);
	}

	@Transactional
	public Pilot searchPilot(Integer id) throws DataAccessException {
		return teamRepository.searchPilot(id);
	}

	@Transactional(readOnly = true)
	public Team findTeamById(int id) throws DataAccessException {
		return teamRepository.findTeamById(id);
	}

	@Transactional
	public Set<Mechanic> getMechanicsById(Integer id) {
		return teamRepository.getMechanicsById(id);
	}

	@Transactional
	public Set<Pilot> getPilotsById(Integer id) {
		return teamRepository.getPilotsById(id);
	}
	
}
