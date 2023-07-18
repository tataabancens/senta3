package ar.edu.itba.paw.webapp.auth.models;

import ar.edu.itba.paw.model.enums.Roles;

import java.time.ZonedDateTime;
import java.util.Set;

public class AuthenticationTokenDetails {
    private final String id;
    private final String username;
    private final Set<Roles> authorities;
    private final ZonedDateTime issuedDate;
    private final ZonedDateTime expirationDate;
    private final boolean isRefreshToken;
    private final Integer userId;

    public AuthenticationTokenDetails(String id, String username, Set<Roles> authorities, ZonedDateTime issuedDate, ZonedDateTime expirationDate, boolean isRefreshToken, Integer userId) {
        this.id = id;
        this.username = username;
        this.authorities = authorities;
        this.issuedDate = issuedDate;
        this.expirationDate = expirationDate;
        this.isRefreshToken = isRefreshToken;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public Set<Roles> getAuthorities() {
        return authorities;
    }

    public ZonedDateTime getIssuedDate() {
        return issuedDate;
    }

    public ZonedDateTime getExpirationDate() {
        return expirationDate;
    }

    public String getId() {
        return id;
    }

    public boolean isRefreshToken() {
        return isRefreshToken;
    }

    public Integer getUserId() {
        return userId;
    }
}
