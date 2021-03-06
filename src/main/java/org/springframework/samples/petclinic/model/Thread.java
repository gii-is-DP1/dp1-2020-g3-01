package org.springframework.samples.petclinic.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "threads")
public class Thread extends BaseEntity{
	 
	@Column(name="title")
	@NotEmpty
	@Size(min = 4, max = 100)
	private String title;
	
	@Column(name="date")
	private Date creationDate;
	
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "thread_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	List<Message> messages;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="username")
	User user;
}
