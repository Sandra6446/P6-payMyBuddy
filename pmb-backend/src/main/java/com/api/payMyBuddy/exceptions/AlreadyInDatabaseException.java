package com.api.payMyBuddy.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception that can be thrown when the requested object is already in database
 */
@Getter
public class AlreadyInDatabaseException extends APIRuntimeException {
    public AlreadyInDatabaseException(String s) {
        super(s);
        this.setHttpStatus(HttpStatus.CONFLICT);
    }
}
