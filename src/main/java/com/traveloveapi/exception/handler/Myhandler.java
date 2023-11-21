package com.traveloveapi.exception.handler;

import com.traveloveapi.DTO.ErrorResponse;
import com.traveloveapi.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.function.EntityResponse;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class Myhandler {

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse custom(RuntimeException ex) {
        return new ErrorResponse(ex.getMessage(), 400);
    }


    @ExceptionHandler(IncorrectCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse incorrectCode() {
        return new ErrorResponse("Incorrect code!", 401);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse incorrectPassword() {
        return new ErrorResponse("Incorrect password!", 401);
    }

    @ExceptionHandler(IncorrectKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse incorrectKey() {
        return new ErrorResponse("Incorrect registration key!", 401);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse userNotFound() {
        return new ErrorResponse("User not found", 404);
    }

    @ExceptionHandler(SaveFileException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse fileSavingError() { return new ErrorResponse("Catch error during save file", 500);}

    @ExceptionHandler(LoadFileException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse fileLoadingError() { return new ErrorResponse("File not found", 404);}

    @ExceptionHandler(RequestParamException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse paramError() { return new ErrorResponse("Please re-check your request param!", 400);}

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse forbiddenError() {
        return new ErrorResponse("Access to the requested resource is forbidden", 403);
    }

    @ExceptionHandler(ExpiredCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse expiredCode() {
        return new ErrorResponse("Your code was expired!", 401);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse duplicatedData() {
        return new ErrorResponse("Something must be unique, eg yourseft <3", 409);
    }
}
