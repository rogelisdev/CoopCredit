package com.coopcredit.credit.application_service.application.usecase;

import com.coopcredit.credit.application_service.domain.model.Afilliate;
import com.coopcredit.credit.application_service.domain.model.CreditApplication;
import com.coopcredit.credit.application_service.domain.model.enums.ApplicationStatus;
import com.coopcredit.credit.application_service.domain.ports.in.CreateCreditAplicationUseCase;
import com.coopcredit.credit.application_service.domain.ports.out.AfilliateRepositoryPort;
import com.coopcredit.credit.application_service.domain.ports.out.CreditApplicationRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateCreditApplicationUseCaseImpl implements CreateCreditAplicationUseCase {

    private final CreditApplicationRepositoryPort repositoryPort;
    private final AfilliateRepositoryPort afilliateRepositoryPort;

    private static final int MINIMUM_SENIORITY_MONTHS = 3;

    @Override
    public CreditApplication create(CreditApplication newCreditApplication) {
        log.info("Creating credit application for affiliate ID: {}", newCreditApplication.getAfilliateId());

        // Fetch affiliate
        Afilliate afilliate = afilliateRepositoryPort.getById(newCreditApplication.getAfilliateId())
                .orElseThrow(() -> {
                    log.error("Affiliate not found with ID: {}", newCreditApplication.getAfilliateId());
                    return new IllegalArgumentException(
                            "Affiliate not found with ID: " + newCreditApplication.getAfilliateId());
                });

        // Business Rule: Affiliate must be active
        if (!afilliate.isActive()) {
            log.error("Affiliate is not active: {}", afilliate.getId());
            throw new IllegalArgumentException("Affiliate must be active to apply for credit");
        }

        // Business Rule: Affiliate seniority >= 3 months
        if (!afilliate.hasMinimumSeniority(MINIMUM_SENIORITY_MONTHS)) {
            log.error("Affiliate does not meet minimum seniority requirement: {}", afilliate.getId());
            throw new IllegalArgumentException(
                    "Affiliate must have at least " + MINIMUM_SENIORITY_MONTHS + " months of seniority");
        }

        // Calculate monthly installment
        BigDecimal monthlyInstallment = newCreditApplication.calculateMonthlyInstallment();
        log.debug("Calculated monthly installment: {}", monthlyInstallment);

        // Business Rule: Monthly installment <= 0.4 × salary
        if (!afilliate.canAffordInstallment(monthlyInstallment)) {
            log.error("Monthly installment exceeds 40% of salary. Installment: {}, Salary: {}",
                    monthlyInstallment, afilliate.getSalary());
            throw new IllegalArgumentException("Monthly installment cannot exceed 40% of salary");
        }

        // Set initial status and requested date
        newCreditApplication.setStatus(ApplicationStatus.PENDING);
        newCreditApplication.setRequestedDate(LocalDateTime.now());

        CreditApplication created = repositoryPort.create(newCreditApplication);
        log.info("Credit application created successfully with ID: {}", created.getId());

        return created;
    }
}
