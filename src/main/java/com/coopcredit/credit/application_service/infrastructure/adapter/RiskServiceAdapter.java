package com.coopcredit.credit.application_service.infrastructure.adapter;

import com.coopcredit.credit.application_service.domain.model.Afilliate;
import com.coopcredit.credit.application_service.domain.model.CreditApplication;
import com.coopcredit.credit.application_service.domain.model.RiskEvaluation;
import com.coopcredit.credit.application_service.domain.model.enums.RiskLevel;
import com.coopcredit.credit.application_service.domain.ports.out.RiskEvaluationPort;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class RiskServiceAdapter implements RiskEvaluationPort {

    private final WebClient riskServiceWebClient;

    @Value("${risk-service.retry.max-attempts}")
    private int maxAttempts;

    @Override
    @Retry(name = "riskService", fallbackMethod = "evaluateRiskFallback")
    public RiskEvaluation evaluateRisk(CreditApplication application, Afilliate afilliate) {
        log.info("Calling risk service for credit application ID: {}", application.getId());

        try {
            RiskEvaluationResponse response = riskServiceWebClient
                    .post()
                    .uri("/api/risk/evaluate")
                    .bodyValue(buildRiskRequest(application, afilliate))
                    .retrieve()
                    .bodyToMono(RiskEvaluationResponse.class)
                    .block();

            if (response == null) {
                log.warn("Risk service returned null response for application ID: {}", application.getId());
                return buildDefaultRiskEvaluation();
            }

            log.info("Risk evaluation completed for application ID: {} with score: {}",
                    application.getId(), response.getScore());

            return RiskEvaluation.builder()
                    .score(response.getScore())
                    .level(response.getLevel())
                    .recommendations(response.getRecommendations())
                    .evaluatedAt(LocalDateTime.now())
                    .creditApplicationId(application.getId())
                    .build();

        } catch (Exception e) {
            log.error("Error calling risk service for application ID: {}", application.getId(), e);
            throw new RuntimeException("Risk service call failed", e);
        }
    }

    /**
     * Fallback method when risk service is unavailable
     */
    public RiskEvaluation evaluateRiskFallback(CreditApplication application, Afilliate afilliate, Exception ex) {
        log.error("Risk service fallback triggered for application ID: {}. Error: {}",
                application.getId(), ex.getMessage());

        // Return a conservative risk evaluation
        return RiskEvaluation.builder()
                .score(50) // Neutral score
                .level(RiskLevel.MEDIUM)
                .recommendations("Risk service unavailable. Manual review required.")
                .evaluatedAt(LocalDateTime.now())
                .creditApplicationId(application.getId())
                .build();
    }

    private RiskEvaluationRequest buildRiskRequest(CreditApplication application, Afilliate afilliate) {
        return RiskEvaluationRequest.builder()
                .documentNumber(afilliate.getDocument())
                .requestedAmount(application.getAmount())
                .term(application.getTerm())
                .monthlyIncome(afilliate.getSalary())
                .seniorityMonths(calculateSeniorityMonths(afilliate))
                .build();
    }

    private int calculateSeniorityMonths(Afilliate afilliate) {
        if (afilliate.getRegistrationDate() == null) {
            return 0;
        }
        return (int) java.time.temporal.ChronoUnit.MONTHS.between(
                afilliate.getRegistrationDate(),
                java.time.LocalDate.now());
    }

    private RiskEvaluation buildDefaultRiskEvaluation() {
        return RiskEvaluation.builder()
                .score(0)
                .level(RiskLevel.HIGH)
                .recommendations("Unable to evaluate risk. Manual review required.")
                .evaluatedAt(LocalDateTime.now())
                .build();
    }

    // DTOs for risk service communication
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    private static class RiskEvaluationRequest {
        private String documentNumber;
        private java.math.BigDecimal requestedAmount;
        private Integer term;
        private java.math.BigDecimal monthlyIncome;
        private Integer seniorityMonths;
    }

    @lombok.Data
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    private static class RiskEvaluationResponse {
        private Integer score;
        private RiskLevel level;
        private String recommendations;
    }
}
