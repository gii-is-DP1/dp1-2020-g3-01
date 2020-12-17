package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Position;
import org.springframework.samples.petclinic.repository.PositionRepository;
import org.springframework.stereotype.Service;

@Service
public class PositionService {
	
	private PositionRepository positionRepository;
	
	@Autowired
	public PositionService (PositionRepository positionRepository) {
		this.positionRepository = positionRepository;
	}
	
	@Autowired
	public void savePosition(Position position) throws DataAccessException {
		positionRepository.save(position);
	}
	
	@Autowired
	public Position findById(int id) throws DataAccessException {
		return positionRepository.findById(id);
	}
	
	@Autowired
	public void remove(int id) throws DataAccessException {
		positionRepository.remove(id);
	}

}
