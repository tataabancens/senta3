package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.webapp.auth.models.ApiErrorDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    ObjectMapper mapper;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ApiErrorDetails errorDetails = new ApiErrorDetails();
        HttpStatus status = HttpStatus.FORBIDDEN;
        errorDetails.setStatus(status.value());
        errorDetails.setTitle(status.getReasonPhrase());
        errorDetails.setMessage(e.getMessage());
        errorDetails.setPath(httpServletRequest.getRequestURI());

        httpServletResponse.setStatus(status.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(httpServletResponse.getWriter(), errorDetails);
    }
}
