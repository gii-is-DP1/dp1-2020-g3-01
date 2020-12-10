/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Manager;
import org.springframework.samples.petclinic.model.Mechanic;
import org.springframework.samples.petclinic.model.Pilot;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.MechanicRepository;
import org.springframework.samples.petclinic.repository.PilotRepository;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class UserService {

	private UserRepository userRepository;
	private PilotRepository pilotRepository;
	private MechanicRepository mechanicRepository;

	@Autowired
	public UserService(UserRepository userRepository, PilotRepository pilotRepository, MechanicRepository mechanicRepository) {
		this.userRepository = userRepository;
		this.pilotRepository = pilotRepository;
		this.mechanicRepository = mechanicRepository;
	}

	@Transactional
	public void saveUser(User user) throws DataAccessException {
		user.setEnabled(true);
		userRepository.save(user);
	}
	
	public Optional<User> findUser(String username) {
		return userRepository.findById(username);
	}
	
//	@Transactional(readOnly = true)
//	public User findUser() throws DataAccessException {
//		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String username = userDetails.getUsername();
//		User res = this.userRepository.findByUsername(username);
//		return res;
//	}
	
	@Transactional(readOnly = true)
	public Pilot findPilot() throws DataAccessException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		Pilot pilot = this.pilotRepository.findByUsername(username);
		return pilot;
	}
	
	@Transactional(readOnly = true)
	public Mechanic findMechanic() throws DataAccessException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		Mechanic mechanic = this.mechanicRepository.findByUserName(username);
		return mechanic;
	}
}
