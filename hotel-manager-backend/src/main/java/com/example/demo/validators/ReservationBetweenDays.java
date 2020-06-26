package com.example.demo.validators;

import javax.security.auth.message.MessagePolicy;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.ANNOTATION_TYPE,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReservationDateBetween.class)
public @interface ReservationBetweenDays {
    String message () default "fromDate value must be before toDate value";
    Class<?>[] groups () default {};
    Class<? extends Payload>[] payload () default {};
}
