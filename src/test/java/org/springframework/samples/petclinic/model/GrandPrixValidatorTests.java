package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.web.GrandPrixValidator;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import javax.validation.Validator;


class GrandPrixValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	// Create new Category Positive Case:
	@Test
	void shouldValidateGrandPrix() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		
		//Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.set(2021, Calendar.NOVEMBER, 30); //Year, month and day of month
		Date date = cal.getTime();
		GrandPrix gp = new GrandPrix();
		gp.setId(10);
		gp.setCircuit("San Lorenzo");
		gp.setDayOfRace(date);
		gp.setDistance(4.6);
		gp.setLaps(3);
		gp.setLocation("Pontevedra");

		Validator validator = createValidator();
		Set<ConstraintViolation<GrandPrix>> constraintViolations = validator.validate(gp);
		assertThat(constraintViolations.size()).isEqualTo(0);

		GrandPrixValidator gpValidator = new GrandPrixValidator();
		Errors errors = new BeanPropertyBindingResult(gp, "grandPrix");
		gpValidator.validate(gp, errors);

		assertThat(errors.hasErrors()).isEqualTo(false);
		assertThat(errors.getErrorCount()).isEqualTo(0);
	}

	// Create new Category Negative Case: invalid circuit input
	@Test
	void shouldNotValidateCircuit() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Calendar cal = Calendar.getInstance();
		cal.set(2021, Calendar.NOVEMBER, 30); //Year, month and day of month
		Date date = cal.getTime();
		GrandPrix gp = new GrandPrix();
		gp.setCircuit("s");
		gp.setDayOfRace(date);
		gp.setDistance(4.6);
		gp.setLaps(3);
		gp.setLocation("Pontevedra");

//		Validator validator = createValidator();
//		Set<ConstraintViolation<GrandPrix>> constraintViolations = validator.validate(gp);
//		assertThat(constraintViolations.size()).isEqualTo(1);
		
		GrandPrixValidator gpValidator = new GrandPrixValidator();
		Errors errors = new BeanPropertyBindingResult(gp, "grandPrix");
		gpValidator.validate(gp, errors);
		List<ObjectError>errorCode = errors.getAllErrors();
		
//		ConstraintViolation<GrandPrix> violation = constraintViolations.iterator().next();
//		assertThat(violation.getPropertyPath().toString()).isEqualTo("circuit");
//		assertThat(violation.getMessage()).isEqualTo("size must be between 3 and 50");
		//System.out.println("Esti de aqui S"+errorCode);
		assertThat(errorCode.contains("El circuito no puede ser nulo y tiene que tener entre 3 y 50 caracteres"));

	}
	
	// Create new Category Negative Case: invalid distance input
	@Test
	void shouldNotValidateDistance() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Calendar cal = Calendar.getInstance();
		cal.set(2021, Calendar.NOVEMBER, 30); //Year, month and day of month
		Date date = cal.getTime();
		GrandPrix gp = new GrandPrix();
		gp.setCircuit("San Lorenzo");
		gp.setDayOfRace(date);
		gp.setDistance(-3.5);
		gp.setLaps(3);
		gp.setLocation("Pontevedra");

//		Validator validator = createValidator();
//		Set<ConstraintViolation<GrandPrix>> constraintViolations = validator.validate(gp);
		
		GrandPrixValidator gpValidator = new GrandPrixValidator();
		Errors errors = new BeanPropertyBindingResult(gp, "grandPrix");
		gpValidator.validate(gp, errors);
		List<ObjectError>errorCode = errors.getAllErrors();
		
//		assertThat(constraintViolations.size()).isEqualTo(1);
//		ConstraintViolation<GrandPrix> violation = constraintViolations.iterator().next();
//		assertThat(violation.getPropertyPath().toString()).isEqualTo("distance");
//		assertThat(violation.getMessage()).isEqualTo("must be between 0 and 200");
		//System.out.println("Esti de aqui S"+errorCode);
		assertThat(errorCode.contains("La distancia del circuito no puede ser negativa o mayor que 200km"));
	}
	
	// Create new Category Negative Case: invalid laps input
	@Test
	void shouldNotValidateLaps() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Calendar cal = Calendar.getInstance();
		cal.set(2021, Calendar.NOVEMBER, 30); //Year, month and day of month
		Date date = cal.getTime();
		GrandPrix gp = new GrandPrix();
		gp.setCircuit("San Lorenzo");
		gp.setDayOfRace(date);
		gp.setDistance(3.5);
		gp.setLaps(-1);
		gp.setLocation("Pontevedra");

//		Validator validator = createValidator();
//		Set<ConstraintViolation<GrandPrix>> constraintViolations = validator.validate(gp);
//		assertThat(constraintViolations.size()).isEqualTo(1);
//		ConstraintViolation<GrandPrix> violation = constraintViolations.iterator().next();
//		assertThat(violation.getPropertyPath().toString()).isEqualTo("laps");
//		assertThat(violation.getMessage()).isEqualTo("must be between 0 and 30");
		
		GrandPrixValidator gpValidator = new GrandPrixValidator();
		Errors errors = new BeanPropertyBindingResult(gp, "grandPrix");
		gpValidator.validate(gp, errors);
		List<ObjectError>errorCode = errors.getAllErrors();
		//System.out.println("Esti de aqui S"+errorCode);
		assertThat(errorCode.contains("El nº de vueltas no puede ser negativo o mayor que 30"));
	}
	
	// Create new Category Negative Case: invalid location input
	@Test
	void shouldNotValidateLocation() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Calendar cal = Calendar.getInstance();
		cal.set(2021, Calendar.NOVEMBER, 30); //Year, month and day of month
		Date date = cal.getTime();
		GrandPrix gp = new GrandPrix();
		gp.setCircuit("San Lorenzo");
		gp.setDayOfRace(date);
		gp.setDistance(3.5);
		gp.setLaps(1);
		gp.setLocation("P");

//		Validator validator = createValidator();
//		Set<ConstraintViolation<GrandPrix>> constraintViolations = validator.validate(gp);
//		assertThat(constraintViolations.size()).isEqualTo(1);
//		ConstraintViolation<GrandPrix> violation = constraintViolations.iterator().next();
//		assertThat(violation.getPropertyPath().toString()).isEqualTo("location");
//		assertThat(violation.getMessage()).isEqualTo("size must be between 3 and 50");
		
		GrandPrixValidator gpValidator = new GrandPrixValidator();
		Errors errors = new BeanPropertyBindingResult(gp, "grandPrix");
		gpValidator.validate(gp, errors);
		List<ObjectError>errorCode = errors.getAllErrors();
		//System.out.println("Esti de aqui S"+errorCode);
		assertThat(errorCode.contains("La localización no puede ser nula y tiene que tener entre 3 y 50 caracteres"));
	}
	
	// Create new Category Negative Case: invalid date input
	@Test
	void shouldNotValidateDate() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		Calendar cal = Calendar.getInstance();
		cal.set(1990, Calendar.JANUARY, 9); //Year, month and day of month
		Date date1 = cal.getTime();
		//Date date = null;
		GrandPrix gp = new GrandPrix();
		gp.setCircuit("San Lorenzo");
		gp.setDayOfRace(date1);
		gp.setDistance(3.5);
		gp.setLaps(1);
		gp.setLocation("Pontevedra");

//		Validator validator = createValidator();
//		Set<ConstraintViolation<GrandPrix>> constraintViolations = validator.validate(gp);
//		assertThat(constraintViolations.size()).isEqualTo(1);
//		ConstraintViolation<GrandPrix> violation = constraintViolations.iterator().next();
//		assertThat(violation.getPropertyPath().toString()).isEqualTo("dayOfRace");
//		assertThat(violation.getMessage()).isEqualTo("must not be null");
		
		GrandPrixValidator gpValidator = new GrandPrixValidator();
		Errors errors = new BeanPropertyBindingResult(gp, "grandPrix");
		gpValidator.validate(gp, errors);
		List<ObjectError>errorCode = errors.getAllErrors();
		//System.out.println("Esti de aqui S"+errorCode);
		
		assertThat(errorCode.contains("La fecha de carrera no puede estar vacía o ser anterior a la fecha actual"));
	}
}