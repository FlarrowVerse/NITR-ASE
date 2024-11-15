package com.ase.rrts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.ase.rrts.exceptions.ResourceNotFoundException;
import com.ase.rrts.model.ResponseMessage;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle ResourceNotFoundException globally
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseMessage> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        // Create a custom error response
        return ResponseEntity.ok(new ResponseMessage("Exception:", ex.getMessage()));
    }

    // You can also handle other exceptions if needed
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> handleGenericException(Exception ex, WebRequest request) {
        return ResponseEntity.ok(new ResponseMessage("Exception:", ex.getMessage()));
    }
}
