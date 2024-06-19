package com.magela.taskmanagementapi.application.exception;

import com.magela.taskmanagementapi.adapters.controller.TaskController;
import com.magela.taskmanagementapi.application.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String AN_ERROR_OCCURRED = "An error occurred: {}";

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        displayErrorReport(ex);
        ErrorResponse errorResponse = new ErrorResponse("Invalid argument", 400, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        displayErrorReport(ex);
        ErrorResponse errorResponse = new ErrorResponse( "Resource not found", 404, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        displayErrorReport(ex);
        ErrorResponse errorResponse = new ErrorResponse( "Conflict", 409, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        displayErrorReport(ex);
        ErrorResponse errorResponse = new ErrorResponse("Internal Server Error", 500, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpServerErrorException.ServiceUnavailable.class)
    public ResponseEntity<ErrorResponse> handleServiceUnavailable(HttpServerErrorException ex) {
        displayErrorReport(ex);
        ErrorResponse errorResponse = new ErrorResponse(  "Service Unavailable", 503, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    private static void displayErrorReport(Exception ex) {
        log.error(AN_ERROR_OCCURRED, ex.getMessage(), ex);
    }
}