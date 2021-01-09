package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Position;
import org.springframework.samples.petclinic.repository.PositionRepository;
import org.springframework.samples.petclinic.service.exceptions.TwoMaxPilotPerTeamException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PositionService {
	
	private PositionRepository repository;
	
	
	@Autowired
	public PositionService(PositionRepository repository) {
		this.repository = repository;
			}
	
	@Transactional(rollbackFor = TwoMaxPilotPerTeamException.class)
	public void savePosition(Position position) throws DataAccessException{
		
			repository.save(position);
		
	}

}
