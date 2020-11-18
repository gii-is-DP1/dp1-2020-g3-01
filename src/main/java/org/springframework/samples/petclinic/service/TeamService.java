package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Manager;
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
}
