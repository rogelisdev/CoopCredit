package com.coopcredit.credit.application_service.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "username required")
    @Schema(description = "users email", examples = "matt@example.com", required = true)
    private String username;

    @NotBlank(message = "password required")
    @Schema(description = "users password", examples = "M123*", required = true)
    private String password;
}
