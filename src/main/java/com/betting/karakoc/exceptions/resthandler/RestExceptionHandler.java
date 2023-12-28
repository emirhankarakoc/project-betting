package com.betting.karakoc.exceptions.resthandler;


import com.betting.karakoc.exceptions.GeneralException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({GeneralException.class})
    public ResponseEntity<Object> handleMailAlreadyDefinedException(GeneralException generalException){
        return     ResponseEntity
                .status(generalException.getHttpStatus())
                .body(generalException.getMessage());

    }











}
