package com.traveloveapi.exception;

public class CustomException extends RuntimeException{
    public int code;
    public CustomException(String msg, int code) {
        super(msg);
        this.code = code;
    }
}
