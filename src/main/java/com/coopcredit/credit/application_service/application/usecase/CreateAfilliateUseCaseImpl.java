package com.coopcredit.credit.application_service.application.usecase;

import com.coopcredit.credit.application_service.domain.model.Afilliate;
import com.coopcredit.credit.application_service.domain.model.enums.AfilliateStatus;
import com.coopcredit.credit.application_service.domain.ports.in.CreateAfilliateUseCase;
import com.coopcredit.credit.application_service.domain.ports.out.AfilliateRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateAfilliateUseCaseImpl implements CreateAfilliateUseCase {

    private final AfilliateRepositoryPort repositoryPort;

    @Override
    public Afilliate create(Afilliate newAfilliate) {
        log.info("Creating new affiliate with document: {}", newAfilliate.getDocument());

        // Business Rule: Validate salary > 0
        if (!newAfilliate.hasValidSalary()) {
            log.error("Invalid salary for affiliate: {}", newAfilliate.getSalary());
            throw new IllegalArgumentException("Salary must be greater than zero");
        }

        // Business Rule: Document must be unique
        if (repositoryPort.existsByDocument(newAfilliate.getDocument())) {
            log.error("Duplicate document number: {}", newAfilliate.getDocument());
            throw new IllegalArgumentException("Document number already exists: " + newAfilliate.getDocument());
        }

        // Set default status to ACTIVE if not provided
        if (newAfilliate.getStatus() == null) {
            newAfilliate.setStatus(AfilliateStatus.ACTIVE);
        }

        // Set registration date to current date if not provided
        if (newAfilliate.getRegistrationDate() == null) {
            newAfilliate.setRegistrationDate(LocalDate.now());
        }

        Afilliate created = repositoryPort.create(newAfilliate);
        log.info("Affiliate created successfully with ID: {}", created.getId());

        return created;
    }
}
