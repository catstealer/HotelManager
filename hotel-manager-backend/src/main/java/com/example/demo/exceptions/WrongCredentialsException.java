package com.example.demo.exceptions;

public class WrongCredentialsException extends RuntimeException {
    private static final String MESSAGE = "Wrong password.";

    public WrongCredentialsException() {
        super(MESSAGE);
    }
}
