package com.example.demo.exceptions;

public class NoSuchUserException extends RuntimeException {

    private static final String MESSAGE = "User with email: %s does not exist.";

    public NoSuchUserException(String name) {
        super(String.format(MESSAGE, name));
    }
}
