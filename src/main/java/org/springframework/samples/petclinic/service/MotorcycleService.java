//package org.springframework.samples.petclinic.service;
//
//import org.springframework.dao.DataAccessException;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.samples.petclinic.model.Motorcycle;
//import org.springframework.samples.petclinic.model.Team;
//import org.springframework.samples.petclinic.repository.MotorcycleRepository;
//import org.springframework.samples.petclinic.repository.TeamRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//public class MotorcycleService{
//	
//	private MotorcycleRepository motorcycleRepository;
//	
//	@Transactional
//	public void saveMoto(Motorcycle motorcycle) throws DataAccessException {
//		motorcycleRepository.save(motorcycle);
//	}
//
//}
