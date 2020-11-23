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
package org.springframework.samples.petclinic.model;


import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * Simple JavaBean domain object representing an person.
 *
 * @author Ken Krebs
 */
@MappedSuperclass
@Getter
@Setter
@Table(name = "persons")
public class Person extends BaseEntity {

	@Column(name = "firstName")
	@NotEmpty
	protected String firstName;

	@Column(name = "lastName")
	@NotEmpty
	protected String lastName;
	
	@Column(name = "birthDate")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	protected LocalDate birthDate;
	
	@Column(name = "residence")
	@NotEmpty	
	protected String residence;
	
	@Column(name = "nationality")
	@NotEmpty
	protected String nationality;
	
	@Pattern(regexp = "\\d{8}[A-HJ-NP-TV-Z]")
	@Column(name =  "dni", unique=true)
	@NotEmpty
	private String dni;
	
	
	
	

}
