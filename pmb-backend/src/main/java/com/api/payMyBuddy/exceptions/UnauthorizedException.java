package com.api.payMyBuddy.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception that can be thrown when the login is not valid.
 */
@Getter
public class UnauthorizedException extends APIRuntimeException {
    public UnauthorizedException(String s) {
        super(s);
        this.setHttpStatus(HttpStatus.UNAUTHORIZED);
    }
}
