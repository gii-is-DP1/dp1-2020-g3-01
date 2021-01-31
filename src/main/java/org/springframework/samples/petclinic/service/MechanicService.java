package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.samples.petclinic.repository.MechanicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MechanicService {

	private MechanicRepository mechanicRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	public MechanicService(MechanicRepository MechanicRepository) {
		this.mechanicRepository = MechanicRepository;
	}

	@Transactional(readOnly = true)
	public Mechanic findMechanicById(int id) throws DataAccessException {
		return mechanicRepository.findById(id);
	}

	@Transactional
	public void saveMechanic(Mechanic mechanic) throws DataAccessException {
		// creating owner

		mechanicRepository.save(mechanic);
		// creating user
		userService.saveUser(mechanic.getUser());
		// creating authorities
		authoritiesService.saveAuthorities(mechanic.getUser().getUsername(), "Mechanic");

	}

	@Transactional(readOnly = true)
	public Collection<Mechanic> findAllMechanic() throws DataAccessException {
		return mechanicRepository.findAll();
	}

}
