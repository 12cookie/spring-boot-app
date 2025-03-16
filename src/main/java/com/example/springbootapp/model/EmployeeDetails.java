package com.example.springbootapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("EmployeeDetails")
public class EmployeeDetails {

	@Id
	private String id;
	private final String name;
	private final int age;
	private final String education;

	public EmployeeDetails(UserRegistrationDetails registrationDetails) {
		super();
		this.id = registrationDetails.getId();
		this.name = registrationDetails.getName();
		this.age = registrationDetails.getAge();
		this.education = registrationDetails.getEducation();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getEducation() {
		return education;
	}
}