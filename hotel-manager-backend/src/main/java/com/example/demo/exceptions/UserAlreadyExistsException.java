package com.example.demo.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "User with email %s  already exists";

    public UserAlreadyExistsException(String name) {
        super(String.format(MESSAGE, name));
    }
}
