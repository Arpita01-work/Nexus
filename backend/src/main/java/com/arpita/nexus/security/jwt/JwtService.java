package com.arpita.nexus.security.jwt;

import com.arpita.nexus.user.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface JwtService {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    String extractUsername(String token);

    UUID extractUserId(String token);

    UUID extractOrganizationId(String token);

    Claims extractAllClaims(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

    boolean isTokenValid(String token);
}
