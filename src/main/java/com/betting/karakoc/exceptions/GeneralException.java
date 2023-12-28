package com.betting.karakoc.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper=false)
public class GeneralException extends RuntimeException{
    @Getter
    private HttpStatus httpStatus;
    public GeneralException(String msg, int code){
        super(msg);


        if (code == 200) this.httpStatus = HttpStatus.OK;
        if (code == 201) this.httpStatus = HttpStatus.CREATED;
        if (code == 400) this.httpStatus = HttpStatus.BAD_REQUEST;
        if (code == 401) this.httpStatus = HttpStatus.UNAUTHORIZED;
        if (code == 403) this.httpStatus = HttpStatus.FORBIDDEN;


    }
}
