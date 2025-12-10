package com.coopcredit.credit.application_service.domain.ports.out;

import com.coopcredit.credit.application_service.domain.model.Afilliate;
import com.coopcredit.credit.application_service.domain.model.CreditApplication;
import com.coopcredit.credit.application_service.domain.model.RiskEvaluation;

/**
 * Port interface for evaluating credit risk through external service
 */
public interface RiskEvaluationPort {

    /**
     * Evaluate credit risk for a given application and affiliate
     * 
     * @param application The credit application to evaluate
     * @param afilliate   The affiliate applying for credit
     * @return RiskEvaluation with score, level, and recommendations
     */
    RiskEvaluation evaluateRisk(CreditApplication application, Afilliate afilliate);
}
