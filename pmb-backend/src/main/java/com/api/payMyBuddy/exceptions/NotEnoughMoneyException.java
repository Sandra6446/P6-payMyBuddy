package com.api.payMyBuddy.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception that can be thrown when the requested object is not found.
 */
public class NotEnoughMoneyException extends APIRuntimeException {
    public NotEnoughMoneyException(String s) {
        super(s);
        this.setHttpStatus(HttpStatus.CONFLICT);
    }
}
