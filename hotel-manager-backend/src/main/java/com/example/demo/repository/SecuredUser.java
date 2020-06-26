package com.example.demo.repository;

import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

//@Secured({"ROLE_USER"})
@Target({METHOD, TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecuredUser {
}
