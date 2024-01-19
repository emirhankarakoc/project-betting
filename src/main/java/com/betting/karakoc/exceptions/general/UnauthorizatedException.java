package com.betting.karakoc.exceptions.general;

import com.betting.karakoc.exceptions.RestException;
import lombok.Getter;
import org.springframework.http.HttpStatus;


public class UnauthorizatedException extends RestException {
    @Getter
    private HttpStatus httpStatus;

    public UnauthorizatedException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    public UnauthorizatedException(String msg) {
        super(msg);
        this.httpStatus = HttpStatus.UNAUTHORIZED;
    }
}

