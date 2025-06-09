package com.example.springbootapp.service;

import com.example.springbootapp.exception.Exception500Handler;
import com.example.springbootapp.util.CommonUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
public class OTPService {

    private final JavaMailSender mailSender;

    private final CacheService cacheService;

    private final CommonUtil commonUtil;

    private static final long OTP_VALIDITY_DURATION = 5 * 60 * 1000;

    @Value("${spring.mail.username}")
    private String sender;

    public OTPService(JavaMailSender mailSender, CacheService cacheService, CommonUtil commonUtil) {
        this.mailSender = mailSender;
        this.cacheService = cacheService;
        this.commonUtil = commonUtil;
    }

    public void generateAndSendOTP(String emailID) {

        String otp = commonUtil.generateOTP();
        String emailContent = commonUtil.buildEmailContent(otp);

        sendOTPEmail(emailID, emailContent);

        String key = commonUtil.base64Encode("otp" + emailID);
        cacheService.saveToCache(key, otp, Duration.ofSeconds(OTP_VALIDITY_DURATION));
        log.debug("OTP sent successfully to email: {}", emailID);
    }

    public void verifyOTP(String email, String enteredOTP) {

        String key = commonUtil.base64Encode("otp" + email);
        String savedOTP = cacheService.getFromCache(key);

        if (savedOTP == null) {
            throw new Exception500Handler("OTP not found");
        }

        if (savedOTP.equals(enteredOTP)) {
            cacheService.deleteFromCache(key);
        } else {
            throw new Exception500Handler("Invalid OTP");
        }
    }

    private void sendOTPEmail(String toEmail, String htmlContent) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(toEmail);
            helper.setSubject("OTP Code for FindMyHome");
            helper.setText(htmlContent, true);
            mailSender.send(message);
        }
        catch (MessagingException e) {
            log.error("Failed to send email to: {}, error message: {}", toEmail, e.getMessage());
            throw new Exception500Handler("Failed to send email to: " + toEmail, e);
        }
    }
}