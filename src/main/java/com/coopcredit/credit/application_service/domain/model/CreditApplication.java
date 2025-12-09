package com.coopcredit.credit.application_service.domain.model;

import com.coopcredit.credit.application_service.domain.model.enums.ApplicationStatus;
import com.coopcredit.credit.application_service.domain.model.enums.RiskLevel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreditApplication {
    private Long id;
    private BigDecimal amount;
    private Integer term;  //Plazo de meses para pagar
    private ApplicationStatus status;
    private LocalDateTime requestedDate;
    private LocalDateTime evaluatedDate;
    private Afilliate afilliate;
    private RiskLevel
}
