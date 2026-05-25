package com.stackroute.grpc.servertwo.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "User does not Exist")
public class UserDoesNotExistException extends Exception{

    public UserDoesNotExistException(String message){
        super(message);
    }
}
