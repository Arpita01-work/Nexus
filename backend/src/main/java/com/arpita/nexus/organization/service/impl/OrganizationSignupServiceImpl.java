
package com.arpita.nexus.organization.service.impl;

import com.arpita.nexus.authentication.dto.OrganizationSignupRequest;
import com.arpita.nexus.authentication.service.AccountActivationService;
import com.arpita.nexus.authentication.util.PasswordGenerator;
import com.arpita.nexus.common.exception.DuplicateResourceException;
import com.arpita.nexus.organization.dto.CreateOrganizationRequest;
import com.arpita.nexus.organization.dto.OrganizationResponse;
import com.arpita.nexus.organization.entity.Organization;
import com.arpita.nexus.organization.mapper.OrganizationMapper;
import com.arpita.nexus.organization.repository.OrganizationRepository;
import com.arpita.nexus.organization.service.OrganizationSignupService;
import com.arpita.nexus.user.entity.Role;
import com.arpita.nexus.user.entity.User;
import com.arpita.nexus.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class OrganizationSignupServiceImpl
    implements OrganizationSignupService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final OrganizationMapper organizationMapper;
    private final PasswordGenerator passwordGenerator;
    private final PasswordEncoder passwordEncoder;
    private final AccountActivationService accountActivationService;

    @Override
    @Transactional
    public OrganizationResponse signup(OrganizationSignupRequest request) {

        String organizationCode = request.getOrganization()
            .getCode()
            .trim()
            .toUpperCase(Locale.ROOT);

        String organizationEmail = request.getOrganization()
            .getEmail()
            .trim()
            .toLowerCase(Locale.ROOT);

        String administratorEmail = request.getAdministrator()
            .getEmail()
            .trim()
            .toLowerCase(Locale.ROOT);

        if (organizationRepository.existsByCode(organizationCode)) {
            throw new DuplicateResourceException(
                "Organization code already exists."
            );
        }

        if (organizationRepository.existsByEmail(organizationEmail)) {
            throw new DuplicateResourceException(
                "Organization email already exists."
            );
        }

        if (userRepository.existsByEmail(administratorEmail)) {
            throw new DuplicateResourceException(
                "Administrator email is already registered."
            );
        }

        CreateOrganizationRequest organizationRequest =
            new CreateOrganizationRequest();

        organizationRequest.setName(
            request.getOrganization().getName().trim()
        );
        organizationRequest.setCode(organizationCode);
        organizationRequest.setEmail(organizationEmail);
        organizationRequest.setPhone(
            request.getOrganization().getPhone()
        );
        organizationRequest.setWebsite(
            request.getOrganization().getWebsite()
        );
        organizationRequest.setDescription(
            request.getOrganization().getDescription()
        );

        Organization organization =
            organizationMapper.toEntity(organizationRequest);

        organization = organizationRepository.save(organization);

        User administrator = new User();

        administrator.setOrganization(organization);
        administrator.setFirstName(
            request.getAdministrator().getFirstName().trim()
        );
        administrator.setLastName(
            request.getAdministrator().getLastName()
        );
        administrator.setEmail(administratorEmail);
        administrator.setPhone(
            request.getAdministrator().getPhone()
        );
        administrator.setJobTitle(
            request.getAdministrator().getJobTitle()
        );
        administrator.setRole(Role.ORGANIZATION_ADMIN);

        administrator.setPassword(
            passwordEncoder.encode(
                passwordGenerator.generateTemporaryPassword()
            )
        );

        administrator.setEnabled(false);
        administrator.setEmailVerified(false);
        administrator.setActive(true);

        User savedAdministrator = userRepository.save(administrator);

        accountActivationService.create(savedAdministrator);

        return organizationMapper.toResponse(organization);
    }
}
