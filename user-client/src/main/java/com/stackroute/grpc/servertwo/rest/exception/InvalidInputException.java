package com.stackroute.grpc.servertwo.rest.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Invalid Input")
public class InvalidInputException extends Exception{
    public InvalidInputException(String message){
        super(message);
    }
}
