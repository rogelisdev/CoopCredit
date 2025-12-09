package com.coopcredit.credit.application_service.domain.ports.in;

import com.coopcredit.credit.application_service.domain.model.RiskEvaluation;

import java.util.List;
import java.util.Optional;

public interface GetRiskEvaluationUseCase {
    List<RiskEvaluation> getAll();
    Optional<RiskEvaluation> getById(Long id);
    Optional<RiskEvaluation> getByCreditApplicationId(Long creditApplicationId);

}
