package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.webapp.auth.basic.BasicAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class JwtFilter extends OncePerRequestFilter {
    private static final int BASIC_TOKEN_OFFSET = 6;
    private static final int JWT_TOKEN_OFFSET = 7;

    private AuthenticationManager authenticationManager;

    public JwtFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // JSON Web Tokens
    // Authorization: Basic asdadawd -- username:password (en base 64)
    // Authorization: Digest asduajsnd -- username:hash(password)
    // Authorization: Bearer asdadawdsa --
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO: Jwt thingy
        String header = httpServletRequest.getHeader("Authorization");
        header = header != null ? header : "";
        if (header.startsWith("Basic ")) {
            String authToken = header.substring(BASIC_TOKEN_OFFSET);
            Authentication authResult = authenticationManager.authenticate(new BasicAuthenticationToken(authToken));
            SecurityContextHolder.getContext().setAuthentication(authResult);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
