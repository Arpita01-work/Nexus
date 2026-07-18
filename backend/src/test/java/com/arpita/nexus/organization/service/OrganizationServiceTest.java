package com.arpita.nexus.organization.service;

import com.arpita.nexus.organization.dto.CreateOrganizationRequest;
import com.arpita.nexus.organization.dto.OrganizationResponse;
import com.arpita.nexus.organization.entity.Organization;
import com.arpita.nexus.organization.mapper.OrganizationMapper;
import com.arpita.nexus.organization.repository.OrganizationRepository;
import com.arpita.nexus.organization.service.impl.OrganizationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OrganizationServiceTest {

    @Mock
    private OrganizationRepository repository;

    @Mock
    private OrganizationMapper mapper;

    @InjectMocks
    private OrganizationServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateOrganization() {

        CreateOrganizationRequest request = new CreateOrganizationRequest();

        request.setName("OpenAI");
        request.setCode("OPENAI");
        request.setEmail("contact@openai.com");

        Organization entity = new Organization();

        entity.setName("OpenAI");
        entity.setCode("OPENAI");
        entity.setEmail("contact@openai.com");

        OrganizationResponse response = new OrganizationResponse();

        response.setName("OpenAI");

        when(repository.existsByCode(any())).thenReturn(false);
        when(repository.existsByEmail(any())).thenReturn(false);
        when(mapper.toEntity(any())).thenReturn(entity);
        when(repository.save(any())).thenReturn(entity);
        when(mapper.toResponse(any())).thenReturn(response);

        OrganizationResponse result = service.createOrganization(request);

        assertThat(result.getName()).isEqualTo("OpenAI");
    }
}