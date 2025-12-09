package com.coopcredit.credit.application_service.domain.ports.out;

import com.coopcredit.credit.application_service.domain.model.RiskEvaluation;

import java.util.List;
import java.util.Optional;

public interface RiskEvaluationRepositoryPort {
    RiskEvaluation create(RiskEvaluation newRiskEvaluation);
    List<RiskEvaluation> getAll();
    Optional<RiskEvaluation> getById(Long id);
    Optional<RiskEvaluation> getByCreditApplicationId(Long creditApplicationId);
    Optional<RiskEvaluation> update(RiskEvaluation updateRiskEvaluation);
    boolean delete(Long id);
}
