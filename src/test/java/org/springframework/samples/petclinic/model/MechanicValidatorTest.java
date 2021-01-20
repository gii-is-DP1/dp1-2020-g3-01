package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.web.MechanicValidator;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class MechanicValidatorTest {
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	//  Create new Category Positive Case: 
	@Test
	void shouldValidateMechanic() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		User user = new User();
		user.setPassword("strongPassword");
		user.setUsername("prettyWoman");
		user.setEnabled(true);

		Mechanic mechanic = new Mechanic();
		
		mechanic.setBirthDate(LocalDate.of(1930, 12, 12));
		mechanic.setDni("29551458Q");
		mechanic.setFirstName("Link");
		mechanic.setLastName("Zelda");
		mechanic.setNationality("Hyrule");
		mechanic.setResidence("Kokiri");
		mechanic.setType(Type.ENGINE);
		mechanic.setUser(user);
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Mechanic>> constraintViolations = validator.validate(mechanic);		
		assertThat(constraintViolations.size()).isEqualTo(0);

		MechanicValidator mechanicValidator = new MechanicValidator();
		Errors errors = new BeanPropertyBindingResult(mechanic, "mechanic");
		mechanicValidator.validate(mechanic, errors);
	
		assertThat(errors.hasErrors()).isEqualTo(false);	
		assertThat(errors.getErrorCount()).isEqualTo(0);			
		
	}
	
	@Test
	void shouldNotValidateMechanic() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		User user = new User();
		user.setPassword("strongPassword");
		user.setUsername("prettyWoman");
		user.setEnabled(true);

		Mechanic mechanic = new Mechanic();
		
		mechanic.setBirthDate(LocalDate.of(1930, 12, 12));
		mechanic.setDni("29551458Q");
		mechanic.setFirstName("");
		mechanic.setLastName("Zelda");
		mechanic.setNationality("Hyrule");
		mechanic.setResidence("Kokiri");
		mechanic.setType(Type.ENGINE);
		mechanic.setUser(user);
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Mechanic>> constraintViolations = validator.validate(mechanic);		
		assertThat(constraintViolations.size()).isEqualTo(1);

		ConstraintViolation<Mechanic> violation = constraintViolations.iterator().next();
		
		assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
		
	}
	
	@Test
	void shouldNotValidateMechanicDNI() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		User user = new User();
		user.setPassword("strongPassword");
		user.setUsername("prettyWoman");
		user.setEnabled(true);

		Mechanic mechanic = new Mechanic();
		
		mechanic.setBirthDate(LocalDate.of(1930, 12, 12));
		mechanic.setDni("29551458Qwqe");
		mechanic.setFirstName("Link");
		mechanic.setLastName("Zelda");
		mechanic.setNationality("Hyrule");
		mechanic.setResidence("Kokiri");
		mechanic.setType(Type.ENGINE);
		mechanic.setUser(user);
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Mechanic>> constraintViolations = validator.validate(mechanic);		
		assertThat(constraintViolations.size()).isEqualTo(1);

		ConstraintViolation<Mechanic> violation = constraintViolations.iterator().next();
		
		assertThat(violation.getPropertyPath().toString()).isEqualTo("dni");
		
	}
	
	@Test
	void shouldNotValidateMechanicNullDate() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		User user = new User();
		user.setPassword("strongPassword");
		user.setUsername("prettyWoman");
		user.setEnabled(true);

		Mechanic mechanic = new Mechanic();
		
		mechanic.setBirthDate(null);
		mechanic.setDni("29551458Q");
		mechanic.setFirstName("Link");
		mechanic.setLastName("Zelda");
		mechanic.setNationality("Hyrule");
		mechanic.setResidence("Kokiri");
		mechanic.setType(Type.ENGINE);
		mechanic.setUser(user);
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Mechanic>> constraintViolations = validator.validate(mechanic);		
		assertThat(constraintViolations.size()).isEqualTo(1);

		ConstraintViolation<Mechanic> violation = constraintViolations.iterator().next();
		
		assertThat(violation.getPropertyPath().toString()).isEqualTo("birthDate");
		
	}
	
	@Test
	void shouldNotValidateMechanicTooYoung() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		User user = new User();
		user.setPassword("strongPassword");
		user.setUsername("prettyWoman");
		user.setEnabled(true);

		Mechanic mechanic = new Mechanic();
		
		mechanic.setBirthDate(LocalDate.of(2020,12,12));
		mechanic.setDni("29551458Q");
		mechanic.setFirstName("Link");
		mechanic.setLastName("Zelda");
		mechanic.setNationality("Hyrule");
		mechanic.setResidence("Kokiri");
		mechanic.setType(Type.ENGINE);
		mechanic.setUser(user);
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Mechanic>> constraintViolations = validator.validate(mechanic);		
		
		MechanicValidator mechanicValidator = new MechanicValidator();
		Errors errors = new BeanPropertyBindingResult(mechanic, "mechanic");
		mechanicValidator.validate(mechanic, errors);
		List<ObjectError> errorCodes= errors.getAllErrors();
			
		assertThat(errorCodes.contains("mechanic.birthDate"));
		
		assertThat(errors.getErrorCount()).isEqualTo(1);	
		
		//assertThat(violation.getPropertyPath().toString()).isEqualTo("birthDate");

		//ConstraintViolation<Mechanic> violation = constraintViolations.iterator().next();
		
		assertThat(errors.getObjectName()).isEqualTo("mechanic");
		
		
		
		
		
	}

}
