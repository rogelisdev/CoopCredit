package com.coopcredit.credit.application_service.infrastructure.controller;

import com.coopcredit.credit.application_service.application.dto.LoginRequest;
import com.coopcredit.credit.application_service.application.dto.RegisterRequest;
import com.coopcredit.credit.application_service.application.dto.TokenResponse;
import com.coopcredit.credit.application_service.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@io.swagger.v3.oas.annotations.tags.Tag(name = "Authentication", description = "Operations related to authentication")
public class AuthController {

    private final AuthService service;

    @io.swagger.v3.oas.annotations.Operation(summary = "Register a new user", description = "Creates a new user account with encrypted password and returns JWT token")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User registered successfully", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = TokenResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request body", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Username already exists", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User registration data", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RegisterRequest.class))) @RequestBody final RegisterRequest request) {
        TokenResponse response = service.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Login user", description = "Authenticates a user and returns a JWT token")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User authenticated successfully", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = TokenResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Invalid credentials", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User login credentials", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = LoginRequest.class))) @RequestBody final LoginRequest request) {
        TokenResponse token = service.login(request);
        return ResponseEntity.ok(token);
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Refresh token", description = "Refreshes the JWT token using an existing valid token")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Token refreshed successfully", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = TokenResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Invalid or expired token", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class)))
    })
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
        TokenResponse token = service.refreshToken(authHeader);
        return ResponseEntity.ok(token);
    }
}
