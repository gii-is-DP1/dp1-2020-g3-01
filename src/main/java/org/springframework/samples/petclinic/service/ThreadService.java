package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.repository.ThreadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.samples.petclinic.model.Thread;

@Service
public class ThreadService {
	
	private ThreadRepository threadRepository;
	
	@Autowired
	public ThreadService(ThreadRepository threadRepository) {
		this.threadRepository = threadRepository;
	}

	@Transactional
	public void saveThread(Thread thread ) throws DataAccessException {
		threadRepository.save(thread);
	}
	
	@Transactional
	public void removeThread(Integer id) throws DataAccessException{
		threadRepository.deleteById(id);
	}
	
	@Transactional
	public Thread findThreadById(Integer id) throws DataAccessException{
		return threadRepository.findThreadById(id);
	}
	
	@Transactional 
	public List<Thread> findAll() throws DataAccessException{
		return threadRepository.finAll();
	}
}
