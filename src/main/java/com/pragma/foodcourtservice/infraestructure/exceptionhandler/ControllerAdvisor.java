package com.pragma.foodcourtservice.infraestructure.exceptionhandler;

import com.pragma.foodcourtservice.domain.exception.DataAlreadyExistsException;
import com.pragma.foodcourtservice.domain.exception.DataNotFoundException;
import com.pragma.foodcourtservice.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
    private final String MESSAGE = "message";
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> body = new HashMap<>();
        methodArgumentNotValidException.getAllErrors().forEach(e ->{
            String fieldName = ((FieldError)e).getField();
            String message = e.getDefaultMessage();
            body.put(fieldName, message);
        });
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleDataAlreadyExistsException(DataAlreadyExistsException dataAlreadyExistsException){
        return new ResponseEntity<>(Collections.singletonMap(MESSAGE, dataAlreadyExistsException.getMessage()),HttpStatus.CONFLICT);
    }
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDataNotFoundException(DataNotFoundException dataNotFoundException){
        return new ResponseEntity<>(Collections.singletonMap(MESSAGE, dataNotFoundException.getMessage()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, String>> handleDomainException(DomainException domainException){
        return new ResponseEntity<>(Collections.singletonMap(MESSAGE, domainException.getMessage()),HttpStatus.CONFLICT);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException){
        return new ResponseEntity<>(Collections.singletonMap(MESSAGE, illegalArgumentException.getMessage()),HttpStatus.BAD_REQUEST);
    }
}
