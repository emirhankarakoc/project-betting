package com.betting.karakoc.exceptions.general;

import com.betting.karakoc.exceptions.RestException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class NotfoundException extends RestException {
    @Getter
    private HttpStatus httpStatus;

    public NotfoundException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    public NotfoundException(String msg) {
        super(msg);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}