
package com.arpita.nexus.user.service;

import com.arpita.nexus.authentication.service.AccountActivationService;
import com.arpita.nexus.authentication.util.PasswordGenerator;
import com.arpita.nexus.common.exception.DuplicateResourceException;
import com.arpita.nexus.common.exception.ResourceNotFoundException;
import com.arpita.nexus.organization.entity.Organization;
import com.arpita.nexus.organization.repository.OrganizationRepository;
import com.arpita.nexus.security.userdetails.CurrentUserProvider;
import com.arpita.nexus.user.dto.CreateUserRequest;
import com.arpita.nexus.user.dto.UpdateUserRequest;
import com.arpita.nexus.user.dto.UserResponse;
import com.arpita.nexus.user.entity.User;
import com.arpita.nexus.user.mapper.UserMapper;
import com.arpita.nexus.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final UserMapper userMapper;
    private final PasswordGenerator passwordGenerator;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
    private final AccountActivationService accountActivationService;
    private final CurrentUserProvider currentUserProvider;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        UUID currentOrganizationId =
            currentUserProvider.getCurrentOrganizationId();

        if (request.getOrganizationId() != null
            && !request.getOrganizationId().equals(currentOrganizationId)) {
            throw new AccessDeniedException(
                "You cannot create users in another organization."
            );
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                "User email already exists."
            );
        }

        Organization organization = organizationRepository
            .findById(currentOrganizationId)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Organization not found."
                )
            );

        User user = userMapper.toEntity(request, organization);

        String temporaryPassword =
            passwordGenerator.generateTemporaryPassword();

        user.setPassword(passwordEncoder.encode(temporaryPassword));
        user.setEnabled(false);
        user.setEmailVerified(false);

        User savedUser = userRepository.save(user);

        accountActivationService.create(savedUser);

        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUser(UUID id) {
        UUID currentOrganizationId =
            currentUserProvider.getCurrentOrganizationId();

        User user = userRepository
            .findByIdAndOrganization_Id(id, currentOrganizationId)
            .orElseThrow(() ->
                new ResourceNotFoundException("User not found.")
            );

        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        UUID currentOrganizationId =
            currentUserProvider.getCurrentOrganizationId();

        return userRepository
            .findAllByOrganization_Id(currentOrganizationId)
            .stream()
            .map(userMapper::toResponse)
            .toList();
    }

    @Override
    public UserResponse updateUser(UUID id, UpdateUserRequest request) {
        UUID currentOrganizationId =
            currentUserProvider.getCurrentOrganizationId();

        User user = userRepository
            .findByIdAndOrganization_Id(id, currentOrganizationId)
            .orElseThrow(() ->
                new ResourceNotFoundException("User not found.")
            );

        if (!user.getEmail().equalsIgnoreCase(request.getEmail())
            && userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                "User email already exists."
            );
        }

        userMapper.updateEntity(user, request);

        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void updateUserStatus(UUID id, boolean active) {
        UUID currentOrganizationId =
            currentUserProvider.getCurrentOrganizationId();

        User user = userRepository
            .findByIdAndOrganization_Id(id, currentOrganizationId)
            .orElseThrow(() ->
                new ResourceNotFoundException("User not found.")
            );

        user.setActive(active);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id) {
        UUID currentOrganizationId =
            currentUserProvider.getCurrentOrganizationId();

        User user = userRepository
            .findByIdAndOrganization_Id(id, currentOrganizationId)
            .orElseThrow(() ->
                new ResourceNotFoundException("User not found.")
            );

        userRepository.delete(user);
    }
}
