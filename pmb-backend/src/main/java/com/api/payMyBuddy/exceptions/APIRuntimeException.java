package com.api.payMyBuddy.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception that can be thrown when the database request failed
 */
public class APIRuntimeException extends RuntimeException {

    protected HttpStatus httpStatus;

    public APIRuntimeException(String s) { super(s);}

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
