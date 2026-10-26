package com.arpita.nexus.authentication.controller;

import com.arpita.nexus.authentication.dto.ActivateAccountRequest;
import com.arpita.nexus.authentication.dto.LoginRequest;
import com.arpita.nexus.authentication.dto.LoginResponse;
import com.arpita.nexus.authentication.service.AuthenticationService;
import com.arpita.nexus.common.response.ApiResponse;
import com.arpita.nexus.common.response.ApiResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arpita.nexus.authentication.dto.RefreshTokenRequest;
import com.arpita.nexus.authentication.dto.RefreshTokenResponse;
import jakarta.validation.Valid;
import com.arpita.nexus.authentication.dto.OrganizationSignupRequest;
import com.arpita.nexus.organization.dto.OrganizationResponse;
import com.arpita.nexus.organization.service.OrganizationSignupService;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final OrganizationSignupService organizationSignupService;

    @PostMapping("/activate")
    public ResponseEntity<ApiResponse<Void>> activateAccount(
        @Valid @RequestBody ActivateAccountRequest request) {

        authenticationService.activateAccount(request);

        return ResponseEntity.ok(
            ApiResponseBuilder.success(
                "Account activated successfully.",
                null
            )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
        @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
            ApiResponseBuilder.success(
                "Login successful.",
                authenticationService.login(request)
            )
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshAccessToken(
        @Valid @RequestBody RefreshTokenRequest request) {

        return ResponseEntity.ok(
            ApiResponseBuilder.success(
                "Access token refreshed successfully.",
                authenticationService.refreshAccessToken(
                    request.getRefreshToken()
                )
            )
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
        @Valid @RequestBody RefreshTokenRequest request) {

        authenticationService.logout(
            request.getRefreshToken()
        );

        return ResponseEntity.ok(
            ApiResponseBuilder.success(
                "Logout successful.",
                null
            )
        );
    }

    @PostMapping("/signup/organization")
    public ResponseEntity<ApiResponse<OrganizationResponse>> signupOrganization(
        @Valid @RequestBody OrganizationSignupRequest request) {

        OrganizationResponse response =
            organizationSignupService.signup(request);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponseBuilder.success(
                "Organization signup successful. Check your email to activate your administrator account.",
                response));
    }
}
