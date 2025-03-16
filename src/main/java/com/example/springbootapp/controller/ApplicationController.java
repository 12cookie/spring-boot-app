package com.example.springbootapp.controller;

import com.example.springbootapp.exception.BadRequestException;
import com.example.springbootapp.exception.Exception500Handler;
import com.example.springbootapp.model.UserRegistrationDetails;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.springbootapp.util.DBUtil;

import java.util.Map;
import java.util.Objects;

@CrossOrigin
@RestController
public class ApplicationController {

    private static final Logger log = LoggerFactory.getLogger(ApplicationController.class);

    final DBUtil dbUtil;

    public ApplicationController(DBUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    @PostMapping(
            path = "/v1/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Map <String, Object>> processJsonData(
            @Valid @RequestBody UserRegistrationDetails registrationDetails,
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestID) {

        log.info("Received request ID: {} to process JSON data", xRequestID);

        if(!Objects.equals(authorization, "Wavemaker")) {
            log.error("Received authorization header is incorrect");
            throw new BadRequestException("Authorization header is incorrect");
        }
        try {
            dbUtil.createEmployee(registrationDetails);
        }
        catch (Exception e) {
            log.error("Internal server error: {}", e.getMessage());
            throw new Exception500Handler("Internal server error", e);
        }
        return new ResponseEntity<> (HttpStatus.OK);
    }
}
