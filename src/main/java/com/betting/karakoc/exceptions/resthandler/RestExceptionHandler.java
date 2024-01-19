package com.betting.karakoc.exceptions.resthandler;


import com.betting.karakoc.exceptions.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({RestException.class})
    public ResponseEntity<Object> handleRestException(RestException generalException) {
        HttpStatus httpStatus = (generalException.getHttpStatus() != null) ? generalException.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(generalException.getHttpStatus())
                .body(generalException.getMessage());

    }


}
