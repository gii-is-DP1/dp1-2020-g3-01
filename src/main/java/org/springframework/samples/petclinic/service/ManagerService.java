package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.ManagerRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManagerService {
	
    private ManagerRepository managerRepository;	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	public ManagerService(ManagerRepository managerRepository) {
		this.managerRepository = managerRepository;
	}	

	@Transactional(readOnly = true)
	public Manager findManagerById(int id) throws DataAccessException {
		return managerRepository.findById(id);
	}
	
	@Transactional
	public void saveManager(Manager manager) throws DataAccessException {
		//creating owner
		managerRepository.save(manager);		
		//creating user
		userService.saveUser(manager.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(manager.getUser().getUsername(), "manager");
	}	
	
	@Transactional(readOnly = true)
	public Manager findOwnerByUserName() throws DataAccessException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		Manager res = this.managerRepository.findByUserName(username);
		return res;
	}
	
	@Transactional(readOnly = true)
	public Manager findByUserName(String name) throws DataAccessException {
		return managerRepository.findByUserName(name);
	}

}
