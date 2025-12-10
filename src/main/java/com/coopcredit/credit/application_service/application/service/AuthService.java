package com.coopcredit.credit.application_service.application.service;

import com.coopcredit.credit.application_service.application.dto.LoginRequest;
import com.coopcredit.credit.application_service.application.dto.RegisterRequest;
import com.coopcredit.credit.application_service.application.dto.TokenResponse;
import com.coopcredit.credit.application_service.domain.model.enums.Role;
import com.coopcredit.credit.application_service.domain.model.enums.TokenType;
import com.coopcredit.credit.application_service.infrastructure.entity.TokenEntity;
import com.coopcredit.credit.application_service.infrastructure.entity.UserEntity;
import com.coopcredit.credit.application_service.infrastructure.repository.TokenRepository;
import com.coopcredit.credit.application_service.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // ------ REGISTER
    public TokenResponse register(RegisterRequest request) {
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        UserEntity savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);
        return new TokenResponse(token);

    }

    // ------LOGIN

    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        revokeAllUserTokens(user);

        String jwtToken = jwtService.generateToken(user);

        saveUserToken(user, jwtToken);

        return new TokenResponse(jwtToken);

    }

    private void revokeAllUserTokens(UserEntity user) {
        List<TokenEntity> validUserTokens = tokenRepository.findAllByUserAndExpiredFalseAndRevokedFalse(user);

        if (validUserTokens.isEmpty()) {
            for (TokenEntity token : validUserTokens) {
                token.setExpired(true);
                token.setRevoked(true);
            }
            tokenRepository.saveAll(validUserTokens);
        }
    }

    private void saveUserToken(UserEntity userEntity, String jwtToken) {
        TokenEntity token = TokenEntity.builder()
                .user(userEntity)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    // ------ REFRESH TOKEN
    public TokenResponse refreshToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        String refreshToken = authHeader.substring(7);
        String username = jwtService.extractUsername(refreshToken);

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String newAccessToken = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, newAccessToken);

        return new TokenResponse(newAccessToken, refreshToken, false, false);
    }

}
