package com.example.springbootapp.model.httpmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordRequest {
    @Email
    @JsonProperty("email")
    @NotBlank(message = "Email is required")
    String email;
}