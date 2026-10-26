package com.arpita.nexus.authentication.service;

import com.arpita.nexus.user.entity.User;

public interface RefreshTokenService {

    String createRefreshToken(User user);

    String refreshAccessToken(String refreshToken);

    void revokeRefreshToken(String refreshToken);
}
