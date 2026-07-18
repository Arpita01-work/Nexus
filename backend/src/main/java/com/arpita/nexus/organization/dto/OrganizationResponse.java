package com.arpita.nexus.organization.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class OrganizationResponse {

    private UUID id;

    private String name;

    private String code;

    private String email;

    private String phone;

    private String website;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}