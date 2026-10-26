package com.arpita.nexus.authentication.controller;

import com.arpita.nexus.authentication.dto.OrganizationSignupRequest;
import com.arpita.nexus.organization.dto.OrganizationResponse;
import com.arpita.nexus.organization.service.OrganizationSignupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.arpita.nexus.security.jwt.JwtService;
import com.arpita.nexus.authentication.service.AuthenticationService;
import com.arpita.nexus.security.userdetails.CustomUserDetailsService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private com.arpita.nexus.authentication.service.AuthenticationService
        authenticationService;

    @MockitoBean
    private OrganizationSignupService organizationSignupService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void shouldSignupOrganization() throws Exception {

        OrganizationSignupRequest request =
            new OrganizationSignupRequest();

        OrganizationSignupRequest.OrganizationDetails organization =
            new OrganizationSignupRequest.OrganizationDetails();

        organization.setName("Example Company");
        organization.setCode("EXAMPLE");
        organization.setEmail("contact@example.com");

        OrganizationSignupRequest.AdministratorDetails administrator =
            new OrganizationSignupRequest.AdministratorDetails();

        administrator.setFirstName("Alex");
        administrator.setLastName("Morgan");
        administrator.setEmail("admin@example.com");

        request.setOrganization(organization);
        request.setAdministrator(administrator);

        when(organizationSignupService.signup(any()))
            .thenReturn(new OrganizationResponse());

        mockMvc.perform(post("/api/v1/auth/signup/organization")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());
    }

    @Test
    void shouldRejectSignupWhenOrganizationDetailsAreMissing()
        throws Exception {

        mockMvc.perform(post("/api/v1/auth/signup/organization")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());
    }
}
