package com.example.springbootapp.util;

import com.example.springbootapp.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class AuthUtil {

    public void authorizeToken(String token) {
        if(!Objects.equals(token, "Wavemaker")) {
            throw new BadRequestException("Authorization header is incorrect");
        }
    }
}