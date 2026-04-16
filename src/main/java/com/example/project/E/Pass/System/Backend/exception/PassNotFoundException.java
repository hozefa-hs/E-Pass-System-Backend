package com.example.project.E.Pass.System.Backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PassNotFoundException extends RuntimeException {
    
    public PassNotFoundException(String message) {
        super(message);
    }
    
    public PassNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
