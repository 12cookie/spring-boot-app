package com.example.springbootapp.validators;

import com.example.springbootapp.annotations.ValidPhoneNumber;
import com.example.springbootapp.model.httpmodels.RegistrationRequest;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, RegistrationRequest> {

    @Override
    public boolean isValid(RegistrationRequest registrationRequest, ConstraintValidatorContext context) {
        if (registrationRequest == null) {
            return true;
        }

        String phoneNumber = registrationRequest.phoneNumber();
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return true;
        }

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber parsedNumber = phoneUtil.parse(phoneNumber, null);
            boolean isValid = phoneUtil.isValidNumber(parsedNumber);

            if (!isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Invalid phone number")
                        .addPropertyNode("phoneNumber")
                        .addConstraintViolation();
            }

            return isValid;
        } catch (NumberParseException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode("phoneNumber")
                    .addConstraintViolation();
            return false;
        }
    }
}