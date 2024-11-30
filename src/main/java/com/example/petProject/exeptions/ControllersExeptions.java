package com.example.petProject.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllersExeptions{
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        return new ResponseEntity<>("Incorrect data entered", HttpStatus.BAD_REQUEST);
    }
}
