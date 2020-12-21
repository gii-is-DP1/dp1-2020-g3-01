package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService {
	
	private MessageRepository messageRepository;
	
	@Autowired
	public MessageService(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@Transactional
	public void saveMessage(Message message) throws DataAccessException {
		messageRepository.save(message);
	}
	
	@Transactional(readOnly = true)
	public Message findMessageById(Integer id) throws DataAccessException{
		return messageRepository.findMessageById(id);
	}
	
	@Transactional
	public void removeMessage(Integer id) throws DataAccessException{
		messageRepository.remove(id);
	}
	
//	public User getUserByMessageId(Integer id) throws DataAccessException {
//		return messageRepository.getUserByMessageId(id);
//	}
	
	

}

