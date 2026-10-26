package com.arpita.nexus.user.controller;

import com.arpita.nexus.common.response.ApiResponse;
import com.arpita.nexus.common.response.ApiResponseBuilder;
import com.arpita.nexus.user.dto.CreateUserRequest;
import com.arpita.nexus.user.dto.UpdateUserRequest;
import com.arpita.nexus.user.dto.UserResponse;
import com.arpita.nexus.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ORGANIZATION_ADMIN')")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody CreateUserRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseBuilder.success(
                        "User created successfully.",
                        userService.createUser(request)
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "User fetched successfully.",
                        userService.getUser(id)
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "Users fetched successfully.",
                        userService.getAllUsers()
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequest request) {

        return ResponseEntity.ok(
                ApiResponseBuilder.success(
                        "User updated successfully.",
                        userService.updateUser(id, request)
                )
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Void>> updateUserStatus(
        @PathVariable UUID id,
        @RequestParam boolean active) {

        userService.updateUserStatus(id, active);

        String message = active
            ? "User activated successfully."
            : "User deactivated successfully.";

        return ResponseEntity.ok(
            ApiResponseBuilder.success(
                message,
                null
            )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable UUID id) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
