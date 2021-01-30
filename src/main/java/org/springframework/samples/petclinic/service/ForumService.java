package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Forum;
import org.springframework.samples.petclinic.repository.ForumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ForumService {
	
	private ForumRepository forumRepository;
	
	@Autowired
	public ForumService(ForumRepository forumRepository) {
		this.forumRepository = forumRepository;
	}

	@Transactional
	public void saveForum(Forum forum ) throws DataAccessException {
		forumRepository.save(forum);
	}
	
	@Transactional(readOnly = true)
	public Forum findForumByTeamId(int id) throws DataAccessException {
		return forumRepository.findForumByTeamId(id);
	}
	
	@Transactional
	public void removeForum(Integer id) throws DataAccessException{
		forumRepository.deleteById(id);
	}
	
	@Transactional
	public Forum findForumById(Integer id) throws DataAccessException{
		return forumRepository.findForumById(id);
	}
	@Transactional
	public List<Forum> findAll() throws DataAccessException{
		return forumRepository.findAll();
	}
}
