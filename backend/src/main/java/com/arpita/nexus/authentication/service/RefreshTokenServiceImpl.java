package com.arpita.nexus.authentication.service;

import com.arpita.nexus.authentication.entity.RefreshToken;
import com.arpita.nexus.authentication.repository.RefreshTokenRepository;
import com.arpita.nexus.common.exception.BadRequestException;
import com.arpita.nexus.common.exception.ResourceNotFoundException;
import com.arpita.nexus.security.jwt.JwtService;
import com.arpita.nexus.user.entity.User;
import com.arpita.nexus.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public String createRefreshToken(User user) {

        String token = jwtService.generateRefreshToken(user);

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setToken(token);
        refreshToken.setRevoked(false);

        refreshToken.setExpiresAt(
            LocalDateTime.now().plusDays(30)
        );

        refreshTokenRepository.save(refreshToken);

        return token;
    }

    @Override
    @Transactional
    public String refreshAccessToken(String tokenValue) {

        RefreshToken refreshToken =
            refreshTokenRepository.findByToken(tokenValue)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Refresh token not found."
                    )
                );

        if (refreshToken.isRevoked()) {
            throw new BadRequestException(
                "Refresh token has been revoked."
            );
        }

        if (refreshToken.getExpiresAt()
            .isBefore(LocalDateTime.now())) {

            throw new BadRequestException(
                "Refresh token has expired."
            );
        }

        if (!jwtService.isTokenValid(tokenValue)) {
            throw new BadRequestException(
                "Invalid refresh token."
            );
        }

        UUID userId =
            jwtService.extractUserId(tokenValue);

        User user =
            userRepository.findById(userId)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "User not found."
                    )
                );

        return jwtService.generateAccessToken(user);
    }


    @Override
    @Transactional
    public void revokeRefreshToken(String tokenValue) {

        RefreshToken refreshToken =
            refreshTokenRepository.findByToken(tokenValue)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Refresh token not found."
                    )
                );

        if (refreshToken.isRevoked()) {
            throw new BadRequestException(
                "Refresh token has already been revoked."
            );
        }

        refreshToken.setRevoked(true);
        refreshToken.setRevokedAt(LocalDateTime.now());

        refreshTokenRepository.save(refreshToken);
    }
}
