package com.coopcredit.credit.application_service.infrastructure.controller;

import com.coopcredit.credit.application_service.infrastructure.entity.UserEntity;
import com.coopcredit.credit.application_service.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@io.swagger.v3.oas.annotations.tags.Tag(name = "User Management", description = "Operations related to user management")
public class UserController {

    private final UserRepository userRepository;

    // Endpoint accessible by any authenticated user
    @io.swagger.v3.oas.annotations.Operation(summary = "Get current user", description = "Retrieves information about the currently authenticated user")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserEntity.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class)))
    })
    @GetMapping("/me")
    public ResponseEntity<UserEntity> getCurrentUser(
            @io.swagger.v3.oas.annotations.Parameter(description = "Username") @RequestParam String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    // Endpoint for ADMIN only
    @io.swagger.v3.oas.annotations.Operation(summary = "Get all users", description = "Retrieves a list of all users. Requires ADMIN role.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of users retrieved successfully", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserEntity.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class)))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // Endpoint to update data (example)
    @io.swagger.v3.oas.annotations.Operation(summary = "Update user", description = "Updates an existing user. Requires USER or ADMIN role.")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User updated successfully", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserEntity.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(
            @io.swagger.v3.oas.annotations.Parameter(description = "User ID") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated user data", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserEntity.class))) @RequestBody UserEntity updateUser) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(updateUser.getUsername());

        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
