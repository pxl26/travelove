package com.traveloveapi.exception.handler;

import com.traveloveapi.DTO.ErrorResponse;
import com.traveloveapi.exception.IncorrectCodeException;
import com.traveloveapi.exception.IncorrectPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Myhandler {

    @ExceptionHandler(IncorrectCodeException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse incorrectCode() {
        return new ErrorResponse("Incorrect code!", 401);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse incorrectPassword() {
        return new ErrorResponse("Incorrect password!", 401);
    }
}
