package ar.edu.itba.paw.webapp.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class ExpiredAuthenticationTokenException extends AuthenticationException {
    public ExpiredAuthenticationTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
