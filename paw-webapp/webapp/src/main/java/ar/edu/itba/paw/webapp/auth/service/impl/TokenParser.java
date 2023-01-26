package ar.edu.itba.paw.webapp.auth.service.impl;

import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.webapp.auth.JwtFilter;
import ar.edu.itba.paw.webapp.auth.exceptions.ExpiredAuthenticationTokenException;
import ar.edu.itba.paw.webapp.auth.exceptions.InvalidAuthenticationTokenException;
import ar.edu.itba.paw.webapp.auth.models.AuthenticationTokenDetails;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TokenParser {
    @Autowired
    Settings settings;

    public AuthenticationTokenDetails parseToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(settings.getSecret())
                    .requireAudience(settings.getAudience())
                    .setAllowedClockSkewSeconds(settings.getClockSkew())
                    .parseClaimsJws(token)
                    .getBody();

            return new AuthenticationTokenDetails(
                    (String) claims.get(Claims.ID),
                    claims.getSubject(),
                    extractAuthoritiesFromClaims(claims),
                    extractIssuedDateFromClaims(claims),
                    extractExpirationDateFromClaims(claims)
            );
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException e) {
            throw new InvalidAuthenticationTokenException("Invalid token", e);
        } catch (ExpiredJwtException e) {
            throw new ExpiredAuthenticationTokenException("The access token expired", e);
        } catch (InvalidClaimException e) {
            throw new InvalidAuthenticationTokenException("Invalid value for claim \"" + e.getClaimName() + "\"", e);
        } catch (Exception e) {
            throw new InvalidAuthenticationTokenException("Invalid token", e);
        }

    }

    private Set<Roles> extractAuthoritiesFromClaims(@NotNull Claims claims) {
        List<String> rolesAsString = (List<String>) claims.getOrDefault(settings.getAuthoritiesClaimName(), new ArrayList<>());
        return rolesAsString.stream().map(Roles::valueOf).collect(Collectors.toSet());
    }

    private ZonedDateTime extractIssuedDateFromClaims(@NotNull Claims claims) {
        return ZonedDateTime.ofInstant(claims.getIssuedAt().toInstant(), ZoneId.systemDefault());
    }

    private ZonedDateTime extractExpirationDateFromClaims(@NotNull Claims claims) {
        return ZonedDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
    }
}
