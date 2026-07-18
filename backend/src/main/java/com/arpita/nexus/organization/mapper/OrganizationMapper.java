package com.arpita.nexus.organization.mapper;

import com.arpita.nexus.organization.dto.CreateOrganizationRequest;
import com.arpita.nexus.organization.dto.OrganizationResponse;
import com.arpita.nexus.organization.dto.UpdateOrganizationRequest;
import com.arpita.nexus.organization.entity.Organization;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class OrganizationMapper {

    public Organization toEntity(CreateOrganizationRequest request) {

        Organization organization = new Organization();

        organization.setId(UUID.randomUUID());

        organization.setName(request.getName());

        organization.setCode(request.getCode().toUpperCase());

        organization.setEmail(request.getEmail());

        organization.setPhone(request.getPhone());

        organization.setWebsite(request.getWebsite());

        organization.setDescription(request.getDescription());

        organization.setCreatedAt(LocalDateTime.now());

        organization.setUpdatedAt(LocalDateTime.now());

        return organization;
    }

    public void updateEntity(UpdateOrganizationRequest request, Organization organization) {

        organization.setName(request.getName());

        organization.setEmail(request.getEmail());

        organization.setPhone(request.getPhone());

        organization.setWebsite(request.getWebsite());

        organization.setDescription(request.getDescription());

        organization.setUpdatedAt(LocalDateTime.now());
    }

    public OrganizationResponse toResponse(Organization organization) {

        OrganizationResponse response = new OrganizationResponse();

        response.setId(organization.getId());

        response.setName(organization.getName());

        response.setCode(organization.getCode());

        response.setEmail(organization.getEmail());

        response.setPhone(organization.getPhone());

        response.setWebsite(organization.getWebsite());

        response.setDescription(organization.getDescription());

        response.setCreatedAt(organization.getCreatedAt());

        response.setUpdatedAt(organization.getUpdatedAt());

        return response;
    }
}