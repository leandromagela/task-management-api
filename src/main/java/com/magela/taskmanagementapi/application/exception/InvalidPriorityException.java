package com.magela.taskmanagementapi.application.exception;

public class InvalidPriorityException extends RuntimeException {
    public InvalidPriorityException(String message) {
        super(message);
    }
}
