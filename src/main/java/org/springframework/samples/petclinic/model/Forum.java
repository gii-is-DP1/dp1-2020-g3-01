package org.springframework.samples.petclinic.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "forums")
public class Forum extends BaseEntity{
	
	@Column(name="name")
	@NotEmpty
	@Size(min = 4, max = 100)
	private String name;
	
	@Column(name="date")
	private Date creationDate;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "forum_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	List<Thread> threads;
	
	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	Team team;

}
