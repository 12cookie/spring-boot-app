package com.example.springbootapp.service;

import com.example.springbootapp.exception.BadRequestException;
import com.example.springbootapp.exception.Exception500Handler;
import com.example.springbootapp.model.User;
import com.example.springbootapp.model.httpmodels.LoginRequest;
import com.example.springbootapp.model.httpmodels.RegistrationRequest;
import com.example.springbootapp.repository.UserRepository;
import com.example.springbootapp.util.CommonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
public class UserService {

    private final CommonUtil commonUtil;

    private final CacheService cacheService;

    private final UserRepository userRepository;

    private static final long OTP_VALIDITY_DURATION = 5 * 60 * 1000;

    public UserService(UserRepository userRepository, CacheService cacheService, CommonUtil commonUtil) {
        this.commonUtil = commonUtil;
        this.cacheService = cacheService;
        this.userRepository = userRepository;
    }

    public void checkDuplicateUser(RegistrationRequest registrationRequest) {

        if(userRepository.findByEmail(registrationRequest.email()) != null) {
            throw new BadRequestException("Email ID already in use");
        }
        if(userRepository.findByUsername(registrationRequest.username()) != null) {
            throw new BadRequestException("Username already in use");
        }
        if(userRepository.findByPhoneNumber(registrationRequest.phoneNumber()) != null) {
            throw new BadRequestException("Phone number already in use");
        }
    }

    public void saveRegistrationDetailsToCache(RegistrationRequest request) {

        try {
            String registrationDetails = commonUtil.deserializeObject(request);
            String key = commonUtil.base64Encode("registration" + request.email());
            cacheService.saveToCache(key, registrationDetails, Duration.ofSeconds(OTP_VALIDITY_DURATION));
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new Exception500Handler("JsonProcessingException");
        }
    }

    public void verifyUser(LoginRequest loginRequest) {
        User user = isUserRegistered(loginRequest.getUsername());
        if(!user.getPassword().equals(loginRequest.getPassword())) {
            throw new BadRequestException("Incorrect password");
        }
    }

    public void saveUser(String email) {

        String key = commonUtil.base64Encode("registration" + email);
        String registrationDetails = cacheService.getFromCache(key);
        if(registrationDetails == null) {
            throw new BadRequestException("Username not found");
        }
        try {
            User user = (User) commonUtil.serializeObject(registrationDetails, User.class);
            userRepository.save(user);
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new Exception500Handler("JsonProcessingException", e);
        }
    }

    public void updatePassword(String username, String newPassword) {
        User user = isUserRegistered(username);
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public User isUserRegistered(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new BadRequestException("User doesn't exist");
        }
        return user;
    }

    public boolean isEmailNotRegistered(String email) {
        return userRepository.findByEmail(email) == null;
    }
}