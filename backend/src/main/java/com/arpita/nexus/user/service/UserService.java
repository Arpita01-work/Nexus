package com.arpita.nexus.user.service;

import com.arpita.nexus.user.dto.CreateUserRequest;
import com.arpita.nexus.user.dto.UpdateUserRequest;
import com.arpita.nexus.user.dto.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse getUser(UUID id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(UUID id, UpdateUserRequest request);

    void deleteUser(UUID id);

}