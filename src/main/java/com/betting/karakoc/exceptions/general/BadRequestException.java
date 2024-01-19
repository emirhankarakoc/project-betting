package com.betting.karakoc.exceptions.general;

import com.betting.karakoc.exceptions.RestException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class BadRequestException extends RestException {
    @Getter
    private HttpStatus httpStatus;

    public BadRequestException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    public BadRequestException(String msg) {
        super(msg);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}
