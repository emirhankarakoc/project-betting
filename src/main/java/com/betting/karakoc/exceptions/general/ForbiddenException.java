package com.betting.karakoc.exceptions.general;

import com.betting.karakoc.exceptions.RestException;
import lombok.Getter;
import org.springframework.http.HttpStatus;


public class ForbiddenException extends RestException {
    @Getter
    private final HttpStatus httpStatus;

    public ForbiddenException(String msg) {
        super(msg);
        this.httpStatus = HttpStatus.FORBIDDEN;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

