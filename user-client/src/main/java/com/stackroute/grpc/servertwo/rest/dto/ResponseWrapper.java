package com.stackroute.grpc.servertwo.rest.dto;

public class ResponseWrapper<T> {
    public boolean success;
    public String message;
    public T data;

    public ResponseWrapper(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
