package com.arpita.nexus.organization.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrganizationRequest {

    @NotBlank(message = "Organization name is required")
    @Size(max = 150)
    private String name;

    @NotBlank(message = "Organization code is required")
    @Size(max = 50)
    private String code;

    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @Size(max = 20)
    private String phone;

    @Size(max = 255)
    private String website;

    private String description;
}