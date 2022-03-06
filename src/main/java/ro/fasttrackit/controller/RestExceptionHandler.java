package ro.fasttrackit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.fasttrackit.exceptions.ResourceNotFoundException;

import java.security.InvalidParameterException;

@RestControllerAdvice
public class RestExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiException handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.warn("Could not find a resource {}.", ex.getMessage());
        return new ApiException(ex.getMessage());
    }

    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiException handleInvalidParameterException(InvalidParameterException ex){
        log.warn(ex.getMessage());
        return new ApiException(ex.getMessage());
    }
}

record ApiException(String message) {
}