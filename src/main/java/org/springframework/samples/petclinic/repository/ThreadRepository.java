package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Thread;

public interface ThreadRepository extends CrudRepository<Thread, Integer>{
	@Query("SELECT thread FROM Thread thread WHERE thread.id =:id")
	public Thread findThreadById(@Param("id") int id);

	@Modifying
	@Query("DELETE FROM Thread thread WHERE thread.id = :id")
	void remove(@Param("id") Integer Id);
	
	@Query("SELECT thread FROM Thread thread WHERE thread.forum.id =:id")
	public Collection<Thread> findThreadsByForumId(@Param("id") int id);
	
}
