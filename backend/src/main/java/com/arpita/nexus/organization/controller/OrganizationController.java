package com.arpita.nexus.organization.controller;

import com.arpita.nexus.common.response.ApiResponse;
import com.arpita.nexus.common.response.ApiResponseBuilder;
import com.arpita.nexus.organization.dto.CreateOrganizationRequest;
import com.arpita.nexus.organization.dto.OrganizationResponse;
import com.arpita.nexus.organization.dto.UpdateOrganizationRequest;
import com.arpita.nexus.organization.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrganizationResponse>> createOrganization(
            @Valid @RequestBody CreateOrganizationRequest request) {

        OrganizationResponse response = organizationService.createOrganization(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseBuilder.success(
                        "Organization created successfully.",
                        response
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrganizationResponse>> getOrganization(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "Organization fetched successfully.",
                        organizationService.getOrganization(id)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrganizationResponse>> updateOrganization(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateOrganizationRequest request) {

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "Organization updated successfully.",
                        organizationService.updateOrganization(id, request)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(
            @PathVariable UUID id) {

        organizationService.deleteOrganization(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrganizationResponse>>> getAll() {
        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "Organizations fetched successfully",
                        organizationService.getAll()
                )
        );
    }


}
