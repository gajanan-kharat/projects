package com.auth.authgateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class TokenAuthenticationException extends AuthenticationException {
    private HttpStatus httpStatus;

    public TokenAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public TokenAuthenticationException(HttpStatus status, String message) {
        super(message);
        this.httpStatus = status;
    }
}
