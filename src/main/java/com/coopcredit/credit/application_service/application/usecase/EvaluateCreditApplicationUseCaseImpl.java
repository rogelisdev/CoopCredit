package com.coopcredit.credit.application_service.application.usecase;

import com.coopcredit.credit.application_service.domain.model.Afilliate;
import com.coopcredit.credit.application_service.domain.model.CreditApplication;
import com.coopcredit.credit.application_service.domain.model.RiskEvaluation;
import com.coopcredit.credit.application_service.domain.model.enums.ApplicationStatus;
import com.coopcredit.credit.application_service.domain.model.enums.RiskLevel;
import com.coopcredit.credit.application_service.domain.ports.in.EvaluateCreditApplicationUseCase;
import com.coopcredit.credit.application_service.domain.ports.out.AfilliateRepositoryPort;
import com.coopcredit.credit.application_service.domain.ports.out.CreditApplicationRepositoryPort;
import com.coopcredit.credit.application_service.domain.ports.out.RiskEvaluationPort;
import com.coopcredit.credit.application_service.domain.ports.out.RiskEvaluationRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class EvaluateCreditApplicationUseCaseImpl implements EvaluateCreditApplicationUseCase {

    private final CreditApplicationRepositoryPort creditApplicationRepository;
    private final AfilliateRepositoryPort afilliateRepository;
    private final RiskEvaluationPort riskEvaluationPort;
    private final RiskEvaluationRepositoryPort riskEvaluationRepository;

    @Override
    public CreditApplication evaluate(Long applicationId) {
        log.info("Evaluating credit application ID: {}", applicationId);

        // Fetch credit application
        CreditApplication application = creditApplicationRepository.getById(applicationId)
                .orElseThrow(() -> {
                    log.error("Credit application not found with ID: {}", applicationId);
                    return new IllegalArgumentException("Credit application not found with ID: " + applicationId);
                });

        // Fetch affiliate
        Afilliate afilliate = afilliateRepository.getById(application.getAfilliateId())
                .orElseThrow(() -> {
                    log.error("Affiliate not found with ID: {}", application.getAfilliateId());
                    return new IllegalArgumentException("Affiliate not found with ID: " + application.getAfilliateId());
                });

        try {
            // Call risk evaluation service
            RiskEvaluation riskEvaluation = riskEvaluationPort.evaluateRisk(application, afilliate);
            log.info("Risk evaluation completed for application ID: {} with score: {}",
                    applicationId, riskEvaluation.getScore());

            // Persist risk evaluation
            RiskEvaluation savedEvaluation = riskEvaluationRepository.create(riskEvaluation);

            // Update application with risk evaluation
            application.setRiskEvaluation(savedEvaluation);

            // Update application status based on risk level
            updateApplicationStatus(application, riskEvaluation.getLevel());
            application.setEvaluatedDate(LocalDateTime.now());

            // Save updated application
            CreditApplication updated = creditApplicationRepository.update(application)
                    .orElseThrow(() -> new RuntimeException("Failed to update credit application"));

            log.info("Credit application ID: {} evaluated and updated to status: {}",
                    applicationId, updated.getStatus());

            return updated;

        } catch (Exception e) {
            log.error("Error evaluating credit application ID: {}", applicationId, e);

            // Update application to ERROR status
            application.setStatus(ApplicationStatus.REJECTED);
            application.setEvaluatedDate(LocalDateTime.now());
            creditApplicationRepository.update(application);

            throw new RuntimeException("Failed to evaluate credit application: " + e.getMessage(), e);
        }
    }

    private void updateApplicationStatus(CreditApplication application, RiskLevel riskLevel) {
        switch (riskLevel) {
            case LOW:
                application.setStatus(ApplicationStatus.APPROVED);
                log.info("Application approved due to LOW risk");
                break;
            case MEDIUM:
                application.setStatus(ApplicationStatus.PENDING);
                log.info("Application set to PENDING for manual review due to MEDIUM risk");
                break;
            case HIGH:
                application.setStatus(ApplicationStatus.REJECTED);
                log.info("Application rejected due to HIGH risk");
                break;
            default:
                application.setStatus(ApplicationStatus.PENDING);
                log.warn("Unknown risk level: {}, setting to PENDING", riskLevel);
        }
    }
}
