package com.arpita.nexus.authentication.service;

import com.arpita.nexus.authentication.dto.ActivateAccountRequest;
import com.arpita.nexus.authentication.dto.LoginRequest;
import com.arpita.nexus.authentication.dto.LoginResponse;
import com.arpita.nexus.authentication.dto.RefreshTokenResponse;

public interface AuthenticationService {

    void activateAccount(ActivateAccountRequest request);

    LoginResponse login(LoginRequest request);

    RefreshTokenResponse refreshAccessToken(String refreshToken);

    void logout(String refreshToken);

}
