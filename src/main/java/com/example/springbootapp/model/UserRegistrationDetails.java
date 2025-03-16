package com.example.springbootapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegistrationDetails {

    @JsonProperty("id")
    @NotBlank(message = "User ID is required")
    private String id;

    @JsonProperty("name")
    @NotBlank(message = "Username is required")
    private String name;

    @JsonProperty("age")
    @Min(value = 1, message = "User age is required")
    private int age;

    @JsonProperty("education")
    @NotBlank(message = "User education is required")
    private String education;

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

    @Override
    public String toString() {
        return "Register [id=" + id + ", name=" + name + ", age=" + age + ", education=" + education + "]";
    }
}