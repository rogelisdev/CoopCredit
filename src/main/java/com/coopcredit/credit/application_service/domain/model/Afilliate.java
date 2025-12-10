package com.coopcredit.credit.application_service.domain.model;

import com.coopcredit.credit.application_service.domain.model.enums.AfilliateStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Afilliate {
    private Long id;
    private String firstName;
    private String lastname;
    private String document;
    private String email;
    private BigDecimal salary;
    private AfilliateStatus status;
    private LocalDate registrationDate;

    /**
     * Check if the affiliate is active
     */
    public boolean isActive() {
        return AfilliateStatus.ACTIVE.equals(this.status);
    }

    /**
     * Check if affiliate has minimum seniority in months
     */
    public boolean hasMinimumSeniority(int months) {
        if (this.registrationDate == null) {
            return false;
        }
        Period period = Period.between(this.registrationDate, LocalDate.now());
        int totalMonths = period.getYears() * 12 + period.getMonths();
        return totalMonths >= months;
    }

    /**
     * Validate if affiliate can afford the monthly installment (40% rule)
     */
    public boolean canAffordInstallment(BigDecimal monthlyInstallment) {
        if (this.salary == null || monthlyInstallment == null) {
            return false;
        }
        BigDecimal maxInstallment = this.salary.multiply(new BigDecimal("0.4"));
        return monthlyInstallment.compareTo(maxInstallment) <= 0;
    }

    /**
     * Validate salary is positive
     */
    public boolean hasValidSalary() {
        return this.salary != null && this.salary.compareTo(BigDecimal.ZERO) > 0;
    }
}