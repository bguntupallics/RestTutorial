package com.example.resttutorial.Exceptions;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class, EmployeeNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleException() {
        return new ResponseEntity<>(new ErrorMessage("Employee Not Found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CannotGetJdbcConnectionException.class, DataAccessResourceFailureException.class})
    public ResponseEntity<ErrorMessage> handleServerException() {
        return new ResponseEntity<>(new ErrorMessage("Cannot Connect to Database"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({HttpServerErrorException.class})
    public ResponseEntity<ErrorMessage> handleServerErrorException() {
        return new ResponseEntity<>(new ErrorMessage("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<ErrorMessage> handleClientException() {
        return new ResponseEntity<>(new ErrorMessage("Invalid Request"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PermissionDeniedDataAccessException.class})
    public ResponseEntity<ErrorMessage> handlePermissionDenied() {
        return new ResponseEntity<>(new ErrorMessage("Permission Denied"), HttpStatus.LOCKED);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleUserNotFound(){
        return new ResponseEntity<>(new ErrorMessage("false"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<ErrorMessage> usernameExists(){
        return new ResponseEntity<>(new ErrorMessage("Username already Exists"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
