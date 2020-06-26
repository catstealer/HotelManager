package com.example.demo.validators;

import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PersonExistsValidator implements ConstraintValidator<PersonExists, Long> {

    @Autowired
    PersonRepository personRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return personRepository.existsById(value);
    }

    @Override
    public void initialize(PersonExists constraintAnnotation) {

    }
}
