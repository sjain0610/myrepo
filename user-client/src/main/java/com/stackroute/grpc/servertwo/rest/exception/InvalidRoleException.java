package com.stackroute.grpc.servertwo.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED,reason = "Invalid Role: Role can be 'Customer' OR 'Bank' OR 'BankEmployee'")
public class InvalidRoleException extends Exception {
}
