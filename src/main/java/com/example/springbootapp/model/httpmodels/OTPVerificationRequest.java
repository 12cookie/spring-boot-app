package com.example.springbootapp.model.httpmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class OTPVerificationRequest {

    @JsonProperty("email")
    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @JsonProperty("otp")
    @NotBlank(message = "OTP is required")
    @Length(min = 6, max = 6)
    private String otp;
}