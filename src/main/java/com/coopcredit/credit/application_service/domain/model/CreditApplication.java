package com.coopcredit.credit.application_service.domain.model;

import com.coopcredit.credit.application_service.domain.model.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditApplication {
    private Long id;
    private BigDecimal amount;
    private Integer term; // Plazo de meses para pagar
    private ApplicationStatus status;
    private LocalDateTime requestedDate;
    private LocalDateTime evaluatedDate;

    // Use ID instead of full object to avoid circular dependency in mappers
    private Long afilliateId;
    private RiskEvaluation riskEvaluation;

    /**
     * Calculate monthly installment based on amount and term
     * Simple calculation: amount / term
     * In real scenario, this would include interest rate
     */
    public BigDecimal calculateMonthlyInstallment() {
        if (this.amount == null || this.term == null || this.term == 0) {
            return BigDecimal.ZERO;
        }
        return this.amount.divide(new BigDecimal(this.term), 2, RoundingMode.HALF_UP);
    }

    /**
     * Approve the credit application
     */
    public void approve() {
        this.status = ApplicationStatus.APPROVED;
        this.evaluatedDate = LocalDateTime.now();
    }

    /**
     * Reject the credit application
     */
    public void reject() {
        this.status = ApplicationStatus.REJECTED;
        this.evaluatedDate = LocalDateTime.now();
    }

    /**
     * Set to pending status
     */
    public void setPending() {
        this.status = ApplicationStatus.PENDING;
        this.requestedDate = LocalDateTime.now();
    }
}
