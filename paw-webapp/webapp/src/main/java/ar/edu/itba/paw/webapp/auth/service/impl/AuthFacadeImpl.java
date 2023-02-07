package ar.edu.itba.paw.webapp.auth.service.impl;

import ar.edu.itba.paw.webapp.auth.basic.BasicAuthenticationToken;
import ar.edu.itba.paw.webapp.auth.service.AuthFacade;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthFacadeImpl implements AuthFacade {
    @Override
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    }
}
