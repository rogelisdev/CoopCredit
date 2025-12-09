package com.coopcredit.credit.application_service.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "username required")
    @Size(min = 3, max = 15, message = "The username must contain between 3 and 15 characters.")
    @Schema(description = "user username", examples = "matt21", required = true)
    private String username;

    /* @NotBlank(message = "email required")
    @Size(min = 3, max = 30, message = "The email must contain between 3 and 15 characters.")
    @Schema(description = "user email", examples = "matt@example.com", required = true)
    private String email; */

    @NotBlank(message = "password required")
    @Size(min = 3, max = 30, message = "The password must contain between 3 and 15 characters.")
    @Schema(description = "user username", examples = "M123*", required = true)
    private String password;
}
