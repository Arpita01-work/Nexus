package com.arpita.nexus.security.jwt;

import com.arpita.nexus.security.config.JwtProperties;
import com.arpita.nexus.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
            jwtProperties.getSecret()
                .getBytes(StandardCharsets.UTF_8)
        );
    }

    @Override
    public String generateAccessToken(User user) {

        Map<String, Object> claims =
            new HashMap<>();

        claims.put(
            "userId",
            user.getId()
        );

        claims.put(
            "organizationId",
            user.getOrganization().getId()
        );

        claims.put(
            "role",
            user.getRole().name()
        );

        claims.put(
            "type",
            "ACCESS"
        );

        return buildToken(
            claims,
            user.getEmail(),
            jwtProperties.getAccessTokenExpiration()
        );
    }

    @Override
    public String generateRefreshToken(User user) {

        Map<String, Object> claims =
            new HashMap<>();

        claims.put(
            "userId",
            user.getId()
        );

        claims.put(
            "type",
            "REFRESH"
        );

        return buildToken(
            claims,
            user.getEmail(),
            jwtProperties.getRefreshTokenExpiration()
        );
    }

    private String buildToken(
        Map<String, Object> claims,
        String subject,
        long expiration
    ) {

        Date now = new Date();

        Date expiry = new Date(
            now.getTime() + expiration
        );

        return Jwts.builder()
            .claims(claims)
            .subject(subject)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(
                getSigningKey(),
                SignatureAlgorithm.HS256
            )
            .compact();
    }

    @Override
    public Claims extractAllClaims(
        String token
    ) {

        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    @Override
    public String extractUsername(
        String token
    ) {

        return extractAllClaims(token)
            .getSubject();
    }

    @Override
    public UUID extractUserId(
        String token
    ) {

        return UUID.fromString(
            extractAllClaims(token)
                .get(
                    "userId",
                    String.class
                )
        );
    }

    @Override
    public UUID extractOrganizationId(
        String token
    ) {

        return UUID.fromString(
            extractAllClaims(token)
                .get(
                    "organizationId",
                    String.class
                )
        );
    }

    @Override
    public boolean isTokenExpired(
        String token
    ) {

        return extractAllClaims(token)
            .getExpiration()
            .before(new Date());
    }

    @Override
    public boolean isTokenValid(
        String token,
        UserDetails userDetails
    ) {

        return extractUsername(token)
            .equals(userDetails.getUsername())
            && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenValid(
        String token
    ) {

        try {

            extractAllClaims(token);

            return !isTokenExpired(token);

        } catch (Exception ex) {

            return false;
        }
    }
}
