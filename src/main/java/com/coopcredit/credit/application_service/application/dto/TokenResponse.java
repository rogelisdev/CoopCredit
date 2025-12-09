package com.coopcredit.credit.application_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    private String token;
    private boolean revoked;
    private String refreshToken;
    private boolean expired;

    public TokenResponse(String token) {
        this.token = token;
        this.revoked = false;
        this.expired = false;
        this.refreshToken = refreshToken;
    }

    public TokenResponse(String token, String refreshToken, boolean expired, boolean revoked) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.expired = expired;
        this.revoked = revoked;
    }
}
