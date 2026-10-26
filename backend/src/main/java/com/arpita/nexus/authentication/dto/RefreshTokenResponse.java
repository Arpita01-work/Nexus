package com.arpita.nexus.authentication.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshTokenResponse {

    private String accessToken;

    private String tokenType;

    private Long expiresIn;
}
