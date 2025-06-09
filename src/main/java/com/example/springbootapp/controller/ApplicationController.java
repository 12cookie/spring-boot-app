package com.example.springbootapp.controller;

import com.example.springbootapp.model.httpmodels.LoginRequest;
import com.example.springbootapp.model.httpmodels.OTPVerificationRequest;
import com.example.springbootapp.model.httpmodels.RegistrationRequest;
import com.example.springbootapp.service.OTPService;
import com.example.springbootapp.service.UserService;
import com.example.springbootapp.util.AuthUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
public class ApplicationController {

    final OTPService otpService;

    final UserService userService;

    final AuthUtil authUtil;

    public ApplicationController(OTPService otpService, UserService userService, AuthUtil authUtil) {
        this.otpService = otpService;
        this.userService = userService;
        this.authUtil = authUtil;
    }

    @PostMapping(
            path = "/v1/registerUser",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <String> registerUser(
            @Valid @RequestBody RegistrationRequest registrationRequest,
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "X-Request-ID") String xRequestID) {

        log.info("Received request ID: {} to register user", xRequestID);

        authUtil.authorizeToken(authorization);
        userService.checkDuplicateUser(registrationRequest);
        otpService.generateAndSendOTP(registrationRequest.email());
        userService.saveRegistrationDetailsToCache(registrationRequest);
        return ResponseEntity.ok("OTP has been sent");
    }

    @PostMapping(
            path = "/v1/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <String> loginUser(
            @Valid @RequestBody LoginRequest loginRequest,
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "X-Request-ID") String xRequestID) {

        log.info("Received request ID: {} to login", xRequestID);

        authUtil.authorizeToken(authorization);
        userService.verifyUser(loginRequest);
        return ResponseEntity.ok("User verified successfully");
    }

    @PostMapping(
            path = "/v1/verifyOTP",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <String> verifyOTP(
            @Valid @RequestBody OTPVerificationRequest request,
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "X-Request-ID") String xRequestID) {

        log.info("Received OTP verification request: {}", xRequestID);

        authUtil.authorizeToken(authorization);
        otpService.verifyOTP(request.getEmail(), request.getOtp());

        if(!Boolean.parseBoolean(request.getIsUserRegistered())) {
            userService.saveUser(request.getEmail());
            return ResponseEntity.ok("User registered successfully");
        }
        return ResponseEntity.ok("OTP verified successfully");
    }

    @PostMapping(
            path = "/v1/forgotPassword",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <String> verifyOTP(
            @Valid @RequestBody @Email @JsonProperty("email") String email,
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "X-Request-ID") String xRequestID) {

        log.info("Received forgot password request for request ID: {}", xRequestID);

        authUtil.authorizeToken(authorization);
        otpService.generateAndSendOTP(email);
        return ResponseEntity.ok("OTP has been sent");
    }

    @PostMapping(
            path = "/v1/updatePassword",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <String> updatePassword(
            @Valid @RequestBody LoginRequest loginRequest,
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "X-Request-ID") String xRequestID) {

        log.info("Received request to update password for request ID: {}", xRequestID);

        authUtil.authorizeToken(authorization);
        userService.updatePassword(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok("Password updated successfully");
    }
}