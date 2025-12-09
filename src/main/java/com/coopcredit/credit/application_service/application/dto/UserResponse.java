package com.coopcredit.credit.application_service.application.dto;

import com.coopcredit.credit.application_service.domain.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String username;
    private String password;
    private Role role;
}
