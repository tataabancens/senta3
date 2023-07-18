package ar.edu.itba.paw.webapp.auth.basic;

import ar.edu.itba.paw.webapp.auth.models.AuthenticationTokenDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class BasicAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private String accessToken;
    private String refreshToken;

    public BasicAuthenticationToken(Object principal, Object credentials,
                                    Collection<? extends GrantedAuthority> authorities,
                                    AuthenticationTokenDetails tokenDetails) {
        super(principal, credentials, authorities);
    }


    public BasicAuthenticationToken(String token) {
        super(null, null);
        this.accessToken = token;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getPrincipal() {
        return (String)super.getPrincipal();
    }

    @Override
    public String getCredentials() {
        return (String)super.getCredentials();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
