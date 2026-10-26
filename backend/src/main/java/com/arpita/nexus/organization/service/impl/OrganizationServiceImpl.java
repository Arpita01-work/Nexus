package com.arpita.nexus.organization.service.impl;

import com.arpita.nexus.common.exception.DuplicateResourceException;
import com.arpita.nexus.common.exception.ResourceNotFoundException;
import com.arpita.nexus.organization.dto.CreateOrganizationRequest;
import com.arpita.nexus.organization.dto.OrganizationResponse;
import com.arpita.nexus.organization.dto.UpdateOrganizationRequest;
import com.arpita.nexus.organization.entity.Organization;
import com.arpita.nexus.organization.exception.OrganizationAlreadyExistsException;
import com.arpita.nexus.organization.exception.OrganizationNotFoundException;
import com.arpita.nexus.organization.mapper.OrganizationMapper;
import com.arpita.nexus.organization.repository.OrganizationRepository;
import com.arpita.nexus.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.arpita.nexus.security.userdetails.CurrentUserProvider;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    private final OrganizationMapper organizationMapper;
    private final CurrentUserProvider currentUserProvider;

    @Override
    public OrganizationResponse createOrganization(CreateOrganizationRequest request) {

        if (organizationRepository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException("Organization code already exists.");
        }

        if (organizationRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Organization email already exists.");
        }

        Organization organization = organizationMapper.toEntity(request);

        organization = organizationRepository.save(organization);

        return organizationMapper.toResponse(organization);
    }

    @Override
    @Transactional(readOnly = true)
    public OrganizationResponse getOrganization(UUID id) {

        java.util.UUID currentOrgId = currentUserProvider.getCurrentOrganizationId();
        if (!id.equals(currentOrgId)) {
            // Hide existence of other organizations
            throw new org.springframework.security.access.AccessDeniedException("Access denied");
        }
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization not found."));

        return organizationMapper.toResponse(organization);
    }

    @Override
    public OrganizationResponse updateOrganization(UUID id,
                                                   UpdateOrganizationRequest request) {

        java.util.UUID currentOrgId = currentUserProvider.getCurrentOrganizationId();
        if (!id.equals(currentOrgId)) {
            throw new org.springframework.security.access.AccessDeniedException("Access denied");
        }
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization not found."));

        organizationMapper.updateEntity(request, organization);

        organization = organizationRepository.save(organization);

        return organizationMapper.toResponse(organization);
    }

    @Override
    public void deleteOrganization(UUID id) {

        java.util.UUID currentOrgId = currentUserProvider.getCurrentOrganizationId();
        if (!id.equals(currentOrgId)) {
            throw new org.springframework.security.access.AccessDeniedException("Access denied");
        }
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Organization not found."));

        organizationRepository.delete(organization);
    }

    @Override
    public OrganizationResponse getById(UUID id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        return organizationMapper.toResponse(organization);
    }

    @Override
    public List<OrganizationResponse> getAll() {
        java.util.UUID currentOrgId = currentUserProvider.getCurrentOrganizationId();
        return organizationRepository.findAll()
                .stream()
                .filter(org -> org.getId().equals(currentOrgId))
                .map(organizationMapper::toResponse)
                .toList();
    }

}
