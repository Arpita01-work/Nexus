
package com.arpita.nexus.authentication.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class OrganizationSignupRequest {

    @NotNull(message = "Organization details are required")
    @Valid
    private OrganizationDetails organization;

    @NotNull(message = "Administrator details are required")
    @Valid
    private AdministratorDetails administrator;

    @Getter
    @Setter
    public static class OrganizationDetails {

        @NotBlank(message = "Organization name is required")
        @Size(max = 150)
        private String name;

        @NotBlank(message = "Organization code is required")
        @Size(max = 50)
        private String code;

        @NotBlank(message = "Organization email is required")
        @Email
        @Size(max = 255)
        private String email;

        @Size(max = 20)
        private String phone;

        @Size(max = 255)
        private String website;

        private String description;
    }

    @Getter
    @Setter
    public static class AdministratorDetails {

        @NotBlank(message = "First name is required")
        @Size(max = 100)
        private String firstName;

        @Size(max = 100)
        private String lastName;

        @NotBlank(message = "Administrator email is required")
        @Email
        @Size(max = 255)
        private String email;

        @Size(max = 20)
        private String phone;

        @Size(max = 100)
        private String jobTitle;
    }
}
