package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.samples.petclinic.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	    

		  List<Person> people = new ArrayList<Person>();
		  Person person1 = new Person();
		  person1.setFirstName("César");
		  person1.setLastName("Gálvez");
		  
		  Person person2 = new Person();
		  person2.setFirstName("Joaquín");
		  person2.setLastName("González");
		  
		  Person person3 = new Person();
		  person3.setFirstName("Diego Miguel");
		  person3.setLastName("Rodríguez");
		  
		  Person person4 = new Person();
		  person4.setFirstName("Rafael");
		  person4.setLastName("Ávila");
		  
		  Person person5 = new Person();
		  person5.setFirstName("Carlos");
		  person5.setLastName("Pardo");
		  
		  people.add(person1);
		  people.add(person2);
		  people.add(person3);
		  people.add(person4);
		  people.add(person5);
		  
		  
		  model.put("people", people);
		  model.put("title", "ForoMotos");
		  model.put("group", "G3-01");
		  
	    return "welcome";
	  }
}
