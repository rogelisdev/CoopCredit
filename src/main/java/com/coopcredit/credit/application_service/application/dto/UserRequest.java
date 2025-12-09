package com.coopcredit.credit.application_service.application.dto;

import com.coopcredit.credit.application_service.domain.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest {

    @NotBlank(message = "username required")
    @Size(min = 3, max = 15, message = "The username must contain between 3 and 15 characters.")
    @Schema(description = "user username", examples = "matt21", required = true)
    private String username;

    @NotBlank(message = "password required")
    @Size(min = 3, max = 15, message = "The password must contain between 3 and 15 characters.")
    @Schema(description = "user password", examples = "M123*", required = true)
    private String password;

    private Role role;
}
