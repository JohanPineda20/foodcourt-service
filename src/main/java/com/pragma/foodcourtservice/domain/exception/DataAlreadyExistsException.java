package com.pragma.foodcourtservice.domain.exception;

public class DataAlreadyExistsException extends RuntimeException{

    public DataAlreadyExistsException(String message){
        super(message);
    }

}
