    package com.arpita.nexus.user.dto;

import com.arpita.nexus.user.entity.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class UserResponse {

    private UUID id;

    private UUID organizationId;

    private String organizationName;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String jobTitle;

    private Role role;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

