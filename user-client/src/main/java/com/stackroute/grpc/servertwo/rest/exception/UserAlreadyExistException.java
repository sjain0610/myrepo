package com.stackroute.grpc.servertwo.rest.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT,reason = "Username already exists")
public class UserAlreadyExistException extends Throwable {
    public UserAlreadyExistException(String message){
        super(message);
    }
}
