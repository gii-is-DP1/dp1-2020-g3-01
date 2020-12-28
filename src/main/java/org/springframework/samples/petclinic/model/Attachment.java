package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "attachments")
public class Attachment extends BaseEntity {

	@Column(name = "url")
	@NotEmpty
	private String url;

	@Column(name = "attachmentType")
	private AttachmentType type;
	
}
