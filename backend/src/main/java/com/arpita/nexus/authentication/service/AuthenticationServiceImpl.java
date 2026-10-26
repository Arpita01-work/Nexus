package com.arpita.nexus.authentication.service;

import com.arpita.nexus.authentication.dto.ActivateAccountRequest;
import com.arpita.nexus.authentication.entity.AccountActivationToken;
import com.arpita.nexus.authentication.entity.TokenStatus;
import com.arpita.nexus.authentication.repository.AccountActivationTokenRepository;
import com.arpita.nexus.common.exception.BadRequestException;
import com.arpita.nexus.common.exception.ResourceNotFoundException;
import com.arpita.nexus.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import com.arpita.nexus.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.arpita.nexus.authentication.dto.LoginRequest;
import com.arpita.nexus.authentication.dto.LoginResponse;
import com.arpita.nexus.authentication.dto.RefreshTokenResponse;


import com.arpita.nexus.security.jwt.JwtService;

import com.arpita.nexus.user.dto.UserResponse;
import com.arpita.nexus.user.mapper.UserMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import com.arpita.nexus.authentication.service.RefreshTokenService;
import com.arpita.nexus.authentication.service.AccountActivationService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountActivationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final AccountActivationService accountActivationService;

    @Override
    @Transactional
    public void activateAccount(ActivateAccountRequest request) {

        AccountActivationToken token = tokenRepository.findByToken(request.getToken())
            .orElseThrow(() ->
                new ResourceNotFoundException("Invalid activation token."));

        if (token.getStatus() != TokenStatus.PENDING) {
            throw new BadRequestException("Activation token is no longer valid.");
        }

        if (!token.getExpiresAt().isAfter(LocalDateTime.now())) {
            accountActivationService.markExpired(token.getId());
            throw new BadRequestException("Activation token has expired.");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match.");
        }

        User user = token.getUser();

        user.setPassword(
            passwordEncoder.encode(request.getNewPassword())
        );

        user.setEnabled(true);

        user.setEmailVerified(true);

        token.setStatus(TokenStatus.ACTIVATED);

        token.setActivatedAt(LocalDateTime.now());

        tokenRepository.save(token);
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {

        try {

            authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(

                    request.getEmail(),
                    request.getPassword()
                )
            );

        } catch (BadCredentialsException ex) {

            throw new BadRequestException("Invalid email or password.");
        }

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() ->
                new ResourceNotFoundException("User not found.")
            );

        UserResponse response = userMapper.toResponse(user);

        String accessToken =
            jwtService.generateAccessToken(user);

        String refreshToken =
            refreshTokenService.createRefreshToken(user);

        return LoginResponse.builder()

            .accessToken(accessToken)

            .refreshToken(refreshToken)

            .tokenType("Bearer")

            .expiresIn(900L)

            .user(response)

            .build();
    }

    @Override
    @Transactional
    public RefreshTokenResponse refreshAccessToken(String refreshToken) {

        String newAccessToken =
            refreshTokenService.refreshAccessToken(refreshToken);

        return RefreshTokenResponse.builder()
            .accessToken(newAccessToken)
            .tokenType("Bearer")
            .expiresIn(900L)
            .build();
    }

    @Override
    @Transactional
    public void logout(String refreshToken) {

        refreshTokenService.revokeRefreshToken(refreshToken);
    }
}
