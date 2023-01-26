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

    public AuthenticationTokenDetails(String id, String username, Set<Roles> authorities, ZonedDateTime issuedDate, ZonedDateTime expirationDate) {
        this.id = id;
        this.username = username;
        this.authorities = authorities;
        this.issuedDate = issuedDate;
        this.expirationDate = expirationDate;
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
}
