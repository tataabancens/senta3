package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.webapp.auth.exceptions.ExpiredAuthenticationTokenException;
import ar.edu.itba.paw.webapp.auth.exceptions.InvalidAuthenticationTokenException;
import ar.edu.itba.paw.webapp.auth.models.ApiErrorDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFailureMapper {
    private AuthenticationFailureMapper() {

    }

    public static void handleFailure(HttpServletRequest request, HttpServletResponse response,
                                     AuthenticationException e, ObjectMapper mapper) throws IOException {

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ApiErrorDetails apiErrorDetails = new ApiErrorDetails();
        if (e instanceof InvalidAuthenticationTokenException || e instanceof InsufficientAuthenticationException) {
            response.addHeader("WWW-Authenticate", "Basic realm=\"myRealm\"");
            response.addHeader("WWW-Authenticate", "Bearer token");
        } else if (e instanceof ExpiredAuthenticationTokenException ){
            response.addHeader("WWW-Authenticate", "Bearer error=\"invalid_token\"");
        }

        apiErrorDetails.setTitle(status.getReasonPhrase());
        apiErrorDetails.setMessage(e.getMessage());
        apiErrorDetails.setStatus(status.value());
        apiErrorDetails.setPath(request.getRequestURI());
        response.setStatus(status.value());
        response.setContentType("application/vnd.api.v1+json");
        mapper.writeValue(response.getWriter(), apiErrorDetails);
    }
}
