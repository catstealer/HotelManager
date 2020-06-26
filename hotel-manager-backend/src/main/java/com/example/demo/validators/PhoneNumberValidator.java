package com.example.demo.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Pattern.compile("\\d{9}").matcher(value).matches();
    }

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
    }
}
