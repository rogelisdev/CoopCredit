package com.coopcredit.credit.application_service.domain.model;

import com.coopcredit.credit.application_service.domain.model.enums.TokenType;

public class Token {
    private Long id;
    private String token;
    private TokenType type;
    private boolean revoked;
    private boolean expired;

    public Token(Long id, String token, TokenType type, boolean revoked, boolean expired) {
        this.id = id;
        this.token = token;
        this.type = type;
        this.revoked = revoked;
        this.expired = expired;
    }

    public Token() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
