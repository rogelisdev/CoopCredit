package com.coopcredit.credit.application_service.domain.ports.in;

import com.coopcredit.credit.application_service.domain.model.RiskEvaluation;

import java.util.Optional;

public interface UpdateRiskEvaluationUseCase {
    Optional<RiskEvaluation> update(Long id, RiskEvaluation updateRiskEvaluation);

}
