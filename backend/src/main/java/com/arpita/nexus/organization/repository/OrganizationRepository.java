package com.arpita.nexus.organization.repository;

import com.arpita.nexus.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

    Optional<Organization> findByCode(String code);

    Optional<Organization> findByEmail(String email);

    boolean existsByCode(String code);

    boolean existsByEmail(String email);
}
