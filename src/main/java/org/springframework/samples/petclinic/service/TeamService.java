package org.springframework.samples.petclinic.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.Team;
import org.springframework.samples.petclinic.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamService {

	private TeamRepository teamRepository;

	@Autowired
	public TeamService(TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}

	@Transactional
	public void saveTeam(Team team) throws DataAccessException {
		teamRepository.save(team);
	}

	@Transactional
	public Integer countTeams(Integer id) throws DataAccessException {
		return teamRepository.countTeams(id);
	}

	@Transactional(readOnly = true)
	public Team findManager(int id) throws DataAccessException {
		return teamRepository.findManager(id);
	}

//	// Pendiente
//	@Transactional(readOnly = true)
//	public Team findTeamByPilotId(int id) throws DataAccessException {
//		return teamRepository.findTeamByPilotId(id);
//	}
//
//	// Pendiente
//	@Transactional(readOnly = true)
//	public Team findTeamByMechanicId(int id) throws DataAccessException {
//		return teamRepository.findTeamByMechanicId(id);
//	}

	@Transactional
	public void removeTeam(Integer id) throws DataAccessException {
		teamRepository.remove(id);
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
}
