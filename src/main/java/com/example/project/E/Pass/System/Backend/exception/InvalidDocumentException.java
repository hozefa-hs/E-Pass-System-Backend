package com.example.project.E.Pass.System.Backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDocumentException extends RuntimeException {
    
    public InvalidDocumentException(String message) {
        super(message);
    }
    
    public InvalidDocumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
