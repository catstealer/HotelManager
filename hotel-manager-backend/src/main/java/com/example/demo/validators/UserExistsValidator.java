package com.example.demo.validators;

import com.example.demo.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UserExistsValidator implements ConstraintValidator<UserExists, Long> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return userRepository.existsById(value);
    }

    @Override
    public void initialize(UserExists constraintAnnotation) {

    }
}
