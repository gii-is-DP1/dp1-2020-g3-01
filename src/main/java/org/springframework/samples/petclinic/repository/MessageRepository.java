package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Attachment;
import org.springframework.samples.petclinic.model.Message;
import org.springframework.samples.petclinic.model.User;

public interface MessageRepository extends CrudRepository<Message, Integer> {

	
	@Query("SELECT message FROM Message message WHERE message.id =:id")
	public Message findMessageById(@Param("id") int id);

	@Modifying
	@Query("DELETE FROM Message message WHERE message.id = :id")
	void remove(@Param("id") Integer Id);
	
//	@Query("SELECT attachment FROM Message message WHERE message.id = :id")
//	Attachment getAttachmentById(@Param("id") int id);

//	@Query("SELECT user FROM Message message WHERE message.user.id = :id")
//	User getUserByMessageId(@Param("id") int id);
	
}

