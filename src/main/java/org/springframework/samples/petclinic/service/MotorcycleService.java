package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Motorcycle;
import org.springframework.samples.petclinic.repository.MotorcycleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MotorcycleService{
	
	private MotorcycleRepository motorcycleRepository;
	
	@Autowired
	public MotorcycleService(MotorcycleRepository motorcycleRepository) {
		this.motorcycleRepository = motorcycleRepository;
	}
	
	@Transactional
	public void saveMoto(Motorcycle motorcycle) throws DataAccessException {
		motorcycleRepository.save(motorcycle);
	}
	
	@Transactional
	public Integer countBikes(int id) throws DataAccessException{
		return motorcycleRepository.countBikes(id);
	}
	
	@Transactional
	public Motorcycle findMotorcycleById(int id) throws DataAccessException{
		return motorcycleRepository.findMotorcycleById(id);
	}
	
	@Transactional
	public Collection<Motorcycle> findAll() throws DataAccessException{
		return motorcycleRepository.findAll();
	}
	

	

}
