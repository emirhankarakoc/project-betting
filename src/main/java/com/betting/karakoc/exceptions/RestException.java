package com.betting.karakoc.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = false)
public class RestException extends RuntimeException {
    @Getter
    private  HttpStatus httpStatus;

    public RestException(String msg) {
        super(msg);

    }

    public RestException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }
}