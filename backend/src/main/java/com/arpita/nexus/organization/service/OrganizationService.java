package com.arpita.nexus.organization.service;

import com.arpita.nexus.organization.dto.CreateOrganizationRequest;
import com.arpita.nexus.organization.dto.OrganizationResponse;
import com.arpita.nexus.organization.dto.UpdateOrganizationRequest;

import java.util.List;
import java.util.UUID;

public interface OrganizationService {

    OrganizationResponse createOrganization(CreateOrganizationRequest request);

    OrganizationResponse getOrganization(UUID id);

    OrganizationResponse updateOrganization(UUID id, UpdateOrganizationRequest request);

    void deleteOrganization(UUID id);

    OrganizationResponse getById(UUID id);

    List<OrganizationResponse> getAll();

}