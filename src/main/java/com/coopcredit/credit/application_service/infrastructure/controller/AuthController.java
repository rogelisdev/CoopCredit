package com.coopcredit.credit.application_service.infrastructure.controller;

import com.coopcredit.credit.application_service.application.dto.LoginRequest;
import com.coopcredit.credit.application_service.application.dto.RegisterRequest;
import com.coopcredit.credit.application_service.application.dto.TokenResponse;
import com.coopcredit.credit.application_service.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody final RegisterRequest request){
        TokenResponse token = service.register(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody final LoginRequest request){
        TokenResponse token = service.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader){
        TokenResponse token = service.refreshToken(authHeader);
        return ResponseEntity.ok(token);
    }
}
