package ar.edu.itba.paw.webapp.auth.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Settings {
    @Value("${authentication.jwt.secret}")
    private String secret;

    @Value("${authentication.jwt.clockSkew}")
    private Long clockSkew;

    @Value("${authentication.jwt.audience}")
    private String audience;

    @Value("${authentication.jwt.issuer}")
    private String issuer;

    @Value("${authentication.jwt.claimNames.authorities}")
    private String authoritiesClaimName;

    @Value("${authentication.jwt.claimNames.userId}")
    private String userIdClaimName;

    @Value("${authentication.jwt.claimNames.isRefreshToken}")
    private String isRefreshTokenClaimName;


    public String getSecret() {
        return secret;
    }

    public Long getClockSkew() {
        return clockSkew;
    }

    public String getAudience() {
        return audience;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getAuthoritiesClaimName() {
        return authoritiesClaimName;
    }

    public String getUserIdClaimName() {
        return userIdClaimName;
    }

    public String getIsRefreshTokenClaimName() {
        return isRefreshTokenClaimName;
    }
}
