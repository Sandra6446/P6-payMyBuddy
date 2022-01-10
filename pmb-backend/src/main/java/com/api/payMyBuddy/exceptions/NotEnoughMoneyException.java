package com.api.payMyBuddy.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception that can be thrown when the user has not enough money for the transaction
 */
public class NotEnoughMoneyException extends APIRuntimeException {
    public NotEnoughMoneyException(String s) {
        super(s);
        this.setHttpStatus(HttpStatus.CONFLICT);
    }
}
