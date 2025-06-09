package com.example.springbootapp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

@Component
@Slf4j
public class CommonUtil {

    private final ObjectMapper objectMapper;

    public CommonUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public String buildEmailContent(String otp) {
        return  "<html>" +
                "<body style='font-family: Arial, sans-serif;'>" +
                "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>" +
                "<h2 style='color: #333; text-align: center;'>OTP Code for FindMyHome</h2>" +
                "<div style='background-color: #f8f9fa; padding: 20px; border-radius: 8px; text-align: center;'>" +
                "<h1 style='color: #007bff; font-size: 32px; margin: 10px 0;'>" + otp + "</h1>" +
                "<p style='color: #666; margin: 10px 0;'>This OTP is valid for 5 minutes</p>" +
                "</div>" +
                "<p style='color: #666; text-align: center; margin-top: 20px;'>" +
                "If you didn't request this OTP, please ignore this email." +
                "</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    public String base64Encode(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }

    public String deserializeObject(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public <T> Object serializeObject(String data, Class <T> valueType) throws JsonProcessingException {
        return objectMapper.readValue(data, valueType);
    }
}