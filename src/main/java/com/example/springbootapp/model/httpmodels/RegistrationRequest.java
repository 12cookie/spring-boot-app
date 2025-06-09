package com.example.springbootapp.model.httpmodels;

import com.example.springbootapp.annotations.ValidPhoneNumber;
import com.example.springbootapp.constants.ValidationErrors;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@ValidPhoneNumber
public record RegistrationRequest(@JsonProperty("name")
								  @NotBlank(message = "Name is required")
								  String name,

								  @JsonProperty("phoneNumber")
								  String phoneNumber,

								  @JsonProperty("email")
								  @Email
								  @NotBlank(message = "Email is required")
								  String email,

								  @JsonProperty("username")
								  @NotBlank(message = "Username is required")
								  String username,

								  @JsonProperty("password")
								  @NotBlank(message = "Password is required")
								  @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}",
										   message = ValidationErrors.incorrectPassword)
								  String password) {
}