package com.magela.taskmanagementapi.application.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("User with this login already exists");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}