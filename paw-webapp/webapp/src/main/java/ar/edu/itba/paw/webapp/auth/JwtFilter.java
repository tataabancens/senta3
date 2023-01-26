package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.webapp.auth.basic.BasicAuthenticationToken;
import ar.edu.itba.paw.webapp.auth.jwt.JwtAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    private static final int BASIC_TOKEN_OFFSET = 6;
    private static final int JWT_TOKEN_OFFSET = 7;

    private final AuthenticationManager authenticationManager;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    public JwtFilter(AuthenticationManager authenticationManager, AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationManager = authenticationManager;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    // JSON Web Tokens
    // Authorization: Basic asdadawd -- username:password (en base 64)
    // Authorization: Digest asduajsnd -- username:hash(password)
    // Authorization: Bearer asdadawdsa --
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String header = httpServletRequest.getHeader("Authorization");
            header = header != null ? header : "";
            if (header.startsWith("Basic ")) {
                String authToken = header.substring(BASIC_TOKEN_OFFSET);
                Authentication authResult = authenticationManager.authenticate(new BasicAuthenticationToken(authToken));
                SecurityContextHolder.getContext().setAuthentication(authResult);

                httpServletResponse.addHeader("Authorization", "Bearer " + ((BasicAuthenticationToken) authResult).getToken());
            } if (header.startsWith("Bearer ")) {
                String authToken = header.substring(JWT_TOKEN_OFFSET);
                Authentication authResult = authenticationManager.authenticate(new JwtAuthenticationToken(authToken));
                SecurityContextHolder.getContext().setAuthentication(authResult);
            }
        } catch (AuthenticationException e) {
            authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
