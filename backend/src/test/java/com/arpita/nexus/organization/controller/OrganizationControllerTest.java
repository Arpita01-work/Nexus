package com.arpita.nexus.organization.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.arpita.nexus.organization.dto.CreateOrganizationRequest;
import com.arpita.nexus.organization.dto.OrganizationResponse;
import com.arpita.nexus.organization.service.OrganizationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.arpita.nexus.security.jwt.JwtService;

import com.arpita.nexus.security.config.SecurityConfig;
import com.arpita.nexus.security.config.PasswordConfig;
import com.arpita.nexus.security.userdetails.CustomUserDetailsService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrganizationController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({SecurityConfig.class, PasswordConfig.class})
class OrganizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrganizationService organizationService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void shouldCreateOrganization() throws Exception {

        CreateOrganizationRequest request = new CreateOrganizationRequest();

        request.setName("OpenAI");
        request.setCode("OPENAI");
        request.setEmail("contact@openai.com");

        when(organizationService.createOrganization(any()))
            .thenReturn(new OrganizationResponse());

        mockMvc.perform(post("/api/v1/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());
    }
}
