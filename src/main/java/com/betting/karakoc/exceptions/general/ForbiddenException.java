package com.betting.karakoc.exceptions.general;

import com.betting.karakoc.exceptions.RestException;
import lombok.Getter;
import org.springframework.http.HttpStatus;


public class ForbiddenException extends RestException {
    @Getter
    private HttpStatus httpStatus;

    public ForbiddenException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    public ForbiddenException(String msg) {
        super(msg);
        this.httpStatus = HttpStatus.FORBIDDEN;
    }
}

