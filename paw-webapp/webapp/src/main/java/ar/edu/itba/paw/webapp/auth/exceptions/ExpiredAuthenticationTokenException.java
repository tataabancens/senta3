package ar.edu.itba.paw.webapp.auth.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.core.AuthenticationException;

public class ExpiredAuthenticationTokenException extends AuthenticationException {
    public ExpiredAuthenticationTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
