package ar.edu.itba.paw.webapp.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidAuthenticationTokenException extends AuthenticationException {
    public InvalidAuthenticationTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
