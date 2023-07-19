package ar.edu.itba.paw.webapp.auth.basic;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.auth.models.AuthenticationTokenDetails;
import ar.edu.itba.paw.webapp.auth.service.AuthenticationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BasicAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService us;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService pawUserDetailsService;

    @Autowired
    private AuthenticationTokenService tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        BasicAuthenticationToken auth = (BasicAuthenticationToken) authentication;
        String[] credentials;
        try {
            credentials = new String(Base64.getDecoder().decode(auth.getAccessToken())).split(":");
        } catch (IllegalArgumentException iae) {
            throw new BadCredentialsException("Invalid basic header");
        }
        if (credentials.length != 2) {
            throw new AuthenticationException("Invalid username/password") {
            };
        }
        User maybeUser = us.getUserByUsername(credentials[0]).orElseThrow(() -> new BadCredentialsException("Bad credentials"));
        if (!passwordEncoder.matches(credentials[1], maybeUser.getPassword())) {
            throw new BadCredentialsException("Bad username/password combination");
        }
        UserDetails userDetails = pawUserDetailsService.loadUserByUsername(credentials[0]);
        String authenticationToken = tokenService.issueAccessToken(credentials[0], mapToAuthority(userDetails.getAuthorities()), maybeUser.getId());
        String refreshToken = tokenService.issueRefreshToken(credentials[0], mapToAuthority(userDetails.getAuthorities()), maybeUser.getId());

        AuthenticationTokenDetails tokenDetails = tokenService.parseToken(authenticationToken);

        BasicAuthenticationToken trustedAuth = new BasicAuthenticationToken(credentials[0], credentials[1],
                userDetails.getAuthorities());

        trustedAuth.setAccessToken(authenticationToken);
        trustedAuth.setRefreshToken(refreshToken);
        return trustedAuth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (BasicAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private Set<Roles> mapToAuthority(Collection<? extends GrantedAuthority> authorities) {
        return  authorities.stream()
                .map(grantedAuthority -> Roles.valueOf(grantedAuthority.toString().split("[_]")[1]))
                .collect(Collectors.toSet());
    }
}
