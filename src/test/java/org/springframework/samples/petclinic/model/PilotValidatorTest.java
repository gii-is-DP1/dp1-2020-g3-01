package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.web.PilotValidator;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class PilotValidatorTest {
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	//  Create new Category Positive Case: 
	@Test
	void shouldValidatePilot() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		User user = new User();
		user.setPassword("strongPassword");
		user.setUsername("prettyWoman");
		user.setEnabled(true);

		Pilot pilot = new Pilot();
		
		pilot.setBirthDate(LocalDate.of(1930, 12, 12));
		pilot.setDni("29551458Q");
		pilot.setFirstName("Link");
		pilot.setLastName("Zelda");
		pilot.setNationality("Hyrule");
		pilot.setHeight(1.65);
		pilot.setWeight(60.5);
		pilot.setNumber(93);
		pilot.setResidence("Kokiri");
		pilot.setUser(user);
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Pilot>> constraintViolations = validator.validate(pilot);		
		assertThat(constraintViolations.size()).isEqualTo(0);

		PilotValidator pilotValidator = new PilotValidator();
		Errors errors = new BeanPropertyBindingResult(pilot, "pilot");
		pilotValidator.validate(pilot, errors);
	
		assertThat(errors.hasErrors()).isEqualTo(false);	
		assertThat(errors.getErrorCount()).isEqualTo(0);			
		
	}
	
	@Test
	void shouldNotValidatePilot() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		User user = new User();
		user.setPassword("strongPassword");
		user.setUsername("prettyWoman");
		user.setEnabled(true);

		Pilot pilot = new Pilot();
		
		pilot.setBirthDate(LocalDate.of(1930, 12, 12));
		pilot.setDni("29551458Q");
		pilot.setFirstName("");
		pilot.setLastName("Zelda");
		pilot.setHeight(1.65);
		pilot.setWeight(60.5);
		pilot.setNumber(93);
		pilot.setNationality("Hyrule");
		pilot.setResidence("Kokiri");
		pilot.setUser(user);
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Pilot>> constraintViolations = validator.validate(pilot);		
		assertThat(constraintViolations.size()).isEqualTo(1);

		ConstraintViolation<Pilot> violation = constraintViolations.iterator().next();
		
		assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
		
	}
	
	@Test
	void shouldNotValidatePilotDNI() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		User user = new User();
		user.setPassword("strongPassword");
		user.setUsername("prettyWoman");
		user.setEnabled(true);

		Pilot pilot = new Pilot();
		
		pilot.setBirthDate(LocalDate.of(1930, 12, 12));
		pilot.setDni("29551458Qwqe");
		pilot.setFirstName("Link");
		pilot.setLastName("Zelda");
		pilot.setHeight(1.65);
		pilot.setWeight(60.5);
		pilot.setNumber(93);
		pilot.setNationality("Hyrule");
		pilot.setResidence("Kokiri");
		
		pilot.setUser(user);
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Pilot>> constraintViolations = validator.validate(pilot);		
		assertThat(constraintViolations.size()).isEqualTo(1);

		ConstraintViolation<Pilot> violation = constraintViolations.iterator().next();
		
		assertThat(violation.getPropertyPath().toString()).isEqualTo("dni");
		
	}
	
	@Test
	void shouldNotValidatePilotNullDate() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		User user = new User();
		user.setPassword("strongPassword");
		user.setUsername("prettyWoman");
		user.setEnabled(true);

		Pilot pilot = new Pilot();
		
		pilot.setBirthDate(null);
		pilot.setDni("29551458Q");
		pilot.setFirstName("Link");
		pilot.setLastName("Zelda");
		pilot.setHeight(1.65);
		pilot.setWeight(60.5);
		pilot.setNumber(93);
		pilot.setNationality("Hyrule");
		pilot.setResidence("Kokiri");
		pilot.setUser(user);
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Pilot>> constraintViolations = validator.validate(pilot);		

		PilotValidator pilotValidator = new PilotValidator();
		Errors errors = new BeanPropertyBindingResult(pilot, "pilot");
		pilotValidator.validate(pilot, errors);
		List<ObjectError> errorCodes= errors.getAllErrors();
			
		assertThat(errorCodes.contains("pilot.birthDate"));
		
		assertThat(errors.getErrorCount()).isEqualTo(1);	
		
		assertThat(errors.getObjectName()).isEqualTo("pilot");
		
	}
	
	@Test
	void shouldNotValidatePilotTooYoung() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		User user = new User();
		user.setPassword("strongPassword");
		user.setUsername("prettyWoman");
		user.setEnabled(true);

		Pilot pilot = new Pilot();
		
		pilot.setBirthDate(LocalDate.of(2020,12,12));
		pilot.setDni("29551458Q");
		pilot.setFirstName("Link");
		pilot.setLastName("Zelda");
		pilot.setHeight(1.65);
		pilot.setWeight(60.5);
		pilot.setNumber(93);
		pilot.setNationality("Hyrule");
		pilot.setResidence("Kokiri");

		pilot.setUser(user);
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Pilot>> constraintViolations = validator.validate(pilot);		
		
		PilotValidator pilotValidator = new PilotValidator();
		Errors errors = new BeanPropertyBindingResult(pilot, "pilot");
		pilotValidator.validate(pilot, errors);
		List<ObjectError> errorCodes= errors.getAllErrors();
			
		assertThat(errorCodes.contains("pilot.birthDate"));
		
		assertThat(errors.getErrorCount()).isEqualTo(1);	
		
		//assertThat(violation.getPropertyPath().toString()).isEqualTo("birthDate");

		//ConstraintViolation<pilot> violation = constraintViolations.iterator().next();
		
		assertThat(errors.getObjectName()).isEqualTo("pilot");
		
	}
	
	@Test
	void shouldNotValidatePilotHeight() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		User user = new User();
		user.setPassword("strongPassword");
		user.setUsername("prettyWoman");
		user.setEnabled(true);

		Pilot pilot = new Pilot();
		
		pilot.setBirthDate(LocalDate.of(1990,12,12));
		pilot.setDni("29551458Q");
		pilot.setFirstName("Link");
		pilot.setLastName("Zelda");
		pilot.setHeight(null);
		pilot.setWeight(60.5);
		pilot.setNumber(93);
		pilot.setNationality("Hyrule");
		pilot.setResidence("Kokiri");
		pilot.setUser(user);
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Pilot>> constraintViolations = validator.validate(pilot);		

		PilotValidator pilotValidator = new PilotValidator();
		Errors errors = new BeanPropertyBindingResult(pilot, "pilot");
		pilotValidator.validate(pilot, errors);
		List<ObjectError> errorCodes= errors.getAllErrors();
			
		assertThat(errorCodes.contains("pilot.height"));
		
		assertThat(errors.getErrorCount()).isEqualTo(1);	
		
		assertThat(errors.getObjectName()).isEqualTo("pilot");
		
	}
	
	@Test
	void shouldNotValidatePilotWeight() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		User user = new User();
		user.setPassword("strongPassword");
		user.setUsername("prettyWoman");
		user.setEnabled(true);

		Pilot pilot = new Pilot();
		
		pilot.setBirthDate(LocalDate.of(1990,12,12));
		pilot.setDni("29551458Q");
		pilot.setFirstName("Link");
		pilot.setLastName("Zelda");
		pilot.setHeight(1.60);
		pilot.setWeight(200.3);
		pilot.setNumber(93);
		pilot.setNationality("Hyrule");
		pilot.setResidence("Kokiri");
		pilot.setUser(user);
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Pilot>> constraintViolations = validator.validate(pilot);		

		PilotValidator pilotValidator = new PilotValidator();
		Errors errors = new BeanPropertyBindingResult(pilot, "pilot");
		pilotValidator.validate(pilot, errors);
		List<ObjectError> errorCodes= errors.getAllErrors();
			
		assertThat(errorCodes.contains("pilot.weight"));
		
		assertThat(errors.getErrorCount()).isEqualTo(1);	
		
		assertThat(errors.getObjectName()).isEqualTo("pilot");
		
	}
	
	@Test
	void shouldNotValidatePilotNumber() {
		
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		User user = new User();
		user.setPassword("strongPassword");
		user.setUsername("prettyWoman");
		user.setEnabled(true);

		Pilot pilot = new Pilot();
		
		pilot.setBirthDate(LocalDate.of(1990,12,12));
		pilot.setDni("29551458Q");
		pilot.setFirstName("Link");
		pilot.setLastName("Zelda");
		pilot.setHeight(1.60);
		pilot.setWeight(50.3);
		pilot.setNumber(-2);
		pilot.setNationality("Hyrule");
		pilot.setResidence("Kokiri");
		pilot.setUser(user);
		
		Validator validator = createValidator();	
		Set<ConstraintViolation<Pilot>> constraintViolations = validator.validate(pilot);		

		PilotValidator pilotValidator = new PilotValidator();
		Errors errors = new BeanPropertyBindingResult(pilot, "pilot");
		pilotValidator.validate(pilot, errors);
		List<ObjectError> errorCodes= errors.getAllErrors();
			
		assertThat(errorCodes.contains("pilot.number"));
		
		assertThat(errors.getErrorCount()).isEqualTo(1);	
		
		assertThat(errors.getObjectName()).isEqualTo("pilot");
		
	}

}
