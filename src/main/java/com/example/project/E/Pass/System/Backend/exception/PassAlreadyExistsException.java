package com.example.project.E.Pass.System.Backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PassAlreadyExistsException extends RuntimeException {
    
    public PassAlreadyExistsException(String message) {
        super(message);
    }
    
    public PassAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
