package com.api.payMyBuddy.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Exception that can be thrown when the requested object is not found.
 */
public class NotFoundInDatabaseException extends APIRuntimeException {
    public NotFoundInDatabaseException(String s) {
        super(s);
        this.setHttpStatus(NOT_FOUND);
    }
}
