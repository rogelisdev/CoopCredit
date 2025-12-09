package com.coopcredit.credit.application_service.domain.ports.in;

import com.coopcredit.credit.application_service.domain.model.RiskEvaluation;

public interface CreateRiskEvaluationUseCase {
    RiskEvaluation create(RiskEvaluation newRiskEvaluation);

}
