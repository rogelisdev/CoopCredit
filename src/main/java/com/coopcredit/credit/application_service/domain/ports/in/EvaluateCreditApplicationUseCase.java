package com.coopcredit.credit.application_service.domain.ports.in;

import com.coopcredit.credit.application_service.domain.model.CreditApplication;

/**
 * Use case for evaluating credit applications using external risk service
 */
public interface EvaluateCreditApplicationUseCase {

    /**
     * Evaluate a credit application and update its status based on risk assessment
     * 
     * @param applicationId The ID of the credit application to evaluate
     * @return The evaluated credit application with updated status and risk
     *         evaluation
     */
    CreditApplication evaluate(Long applicationId);
}
