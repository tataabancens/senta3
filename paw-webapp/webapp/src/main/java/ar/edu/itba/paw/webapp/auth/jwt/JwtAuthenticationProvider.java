package ar.edu.itba.paw.webapp.auth.jwt;

import ar.edu.itba.paw.webapp.auth.models.AuthenticationTokenDetails;
import ar.edu.itba.paw.webapp.auth.service.AuthenticationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    AuthenticationTokenService authenticationTokenService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationTokenService tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        AuthenticationTokenDetails authTokenDetails = authenticationTokenService.parseToken(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(authTokenDetails.getUsername());
        JwtAuthenticationToken jwtAuthToken =  new JwtAuthenticationToken(userDetails, authTokenDetails, userDetails.getAuthorities());
        if (authTokenDetails.isRefreshToken()) {
            String accessTokenToken = tokenService.issueAccessToken( authTokenDetails.getUsername(),
                    authTokenDetails.getAuthorities(),
                    authTokenDetails.getUserId());
            jwtAuthToken.setAccessToken(accessTokenToken);
            jwtAuthToken.setRefreshToken(true);
        }
        return jwtAuthToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
