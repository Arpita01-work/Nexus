package com.arpita.nexus.user.mapper;

import com.arpita.nexus.organization.entity.Organization;
import com.arpita.nexus.user.dto.CreateUserRequest;
import com.arpita.nexus.user.dto.UpdateUserRequest;
import com.arpita.nexus.user.dto.UserResponse;
import com.arpita.nexus.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(
        CreateUserRequest request,
        Organization organization
    ) {

        User user = new User();

        user.setOrganization(organization);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setJobTitle(request.getJobTitle());

        return user;
    }

    public void updateEntity(
        User user,
        UpdateUserRequest request
    ) {

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setJobTitle(request.getJobTitle());
        if (request.getActive() != null) {
            user.setActive(request.getActive());
        }
    }

    public UserResponse toResponse(User user) {

        return UserResponse.builder()
            .id(user.getId())
            .organizationId(user.getOrganization().getId())
            .organizationName(user.getOrganization().getName())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .jobTitle(user.getJobTitle())
            .role(user.getRole())
            .active(user.getActive())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
    }
}
