package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Forum;

import org.springframework.data.repository.CrudRepository;

public interface ForumRepository extends CrudRepository<Forum, Integer>{
	@Query("SELECT forum FROM Forum forum WHERE forum.id =:id")
	public Forum findForumById(@Param("id") int id);
	
	@Query("SELECT forum FROM Forum forum WHERE forum.team.id =:id")
	public Forum findForumByTeamId(@Param("id") int id);

	@Modifying
	@Query("DELETE FROM Forum forum WHERE forum.id = :id")
	void remove(@Param("id") Integer Id);
	
}
