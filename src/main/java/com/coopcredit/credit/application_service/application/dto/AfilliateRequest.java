package com.coopcredit.credit.application_service.application.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AfilliateRequest {

    @NotBlank(message = "Document is required")

    @Size(min = 8, max = 15, message = "Document must be between 8 and 15 characters")

    private String document;

    @NotBlank(message = "First name is required")

    @Size(min = 2, max = 100)

    private String firstName;

    @NotBlank(message = "Last name is required")

    @Size(min = 2, max = 100)

    private String lastname;

    @NotBlank(message = "Email is required")

    @Email(message = "Invalid email")

    private String email;

    @NotNull(message = "Salary is required")

    @DecimalMin(value = "0.01", message = "Salary must be greater than 0")

    private BigDecimal salary;
}