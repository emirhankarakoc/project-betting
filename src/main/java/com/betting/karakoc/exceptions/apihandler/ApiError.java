package com.betting.karakoc.exceptions.apihandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class ApiError {
    private int errorCode;
    private String errorDescription;

}
