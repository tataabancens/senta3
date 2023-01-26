package ar.edu.itba.paw.webapp.auth.service.impl;

import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.webapp.auth.models.AuthenticationTokenDetails;
import ar.edu.itba.paw.webapp.auth.service.AuthenticationTokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Component
public class AuthenticationTokenServiceImpl implements AuthenticationTokenService {
    @Value("${authentication.jwt.validFor}")
    private Long validFor;

    @Autowired
    Settings settings;

    @Autowired
    TokenParser tokenParser;

    @Override
    public String issueToken(String username, Set<Roles> authorities) {
        String id = UUID.randomUUID().toString();
        ZonedDateTime issuedDate = ZonedDateTime.now();
        ZonedDateTime expirationDate = issuedDate.plusSeconds(validFor);

        return Jwts.builder()
                .setId(id)
                .setIssuer(settings.getIssuer())
                .setAudience(settings.getAudience())
                .setSubject(username)
                .setIssuedAt(Date.from(issuedDate.toInstant()))
                .setExpiration(Date.from(expirationDate.toInstant()))
                .claim(settings.getAuthoritiesClaimName(), authorities)
                .signWith(SignatureAlgorithm.HS512, settings.getSecret())
                .compact();
    }

    @Override
    public AuthenticationTokenDetails parseToken(String token) {
        return tokenParser.parseToken(token);
    }
}
