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

	public EmployeeDetails(String id, String name, int age, String education) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.education = education;
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