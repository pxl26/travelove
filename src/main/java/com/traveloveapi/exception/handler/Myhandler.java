package com.traveloveapi.exception.handler;

import com.traveloveapi.DTO.ErrorResponse;
import com.traveloveapi.exception.*;
import jakarta.persistence.NoResultException;
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

    @ExceptionHandler(IncorrectKeyException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse incorrectKey() {
        return new ErrorResponse("Incorrect registration key!", 401);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse userNotFound() {
        return new ErrorResponse("User not found", 404);
    }

    @ExceptionHandler(FileException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse fileError() { return new ErrorResponse("There are some error with file handling", 500);}
}
