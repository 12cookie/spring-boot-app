package com.example.springbootapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("EmployeeDetails")
public class EmployeeDetails {

	@Id
	private String id;
	
	private String name;
	private int age;
	private String education;

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

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}
}