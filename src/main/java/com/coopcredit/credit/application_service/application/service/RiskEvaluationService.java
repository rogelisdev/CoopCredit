package com.coopcredit.credit.application_service.application.service;

import com.coopcredit.credit.application_service.domain.model.RiskEvaluation;
import com.coopcredit.credit.application_service.domain.ports.in.CreateRiskEvaluationUseCase;
import com.coopcredit.credit.application_service.domain.ports.in.DeleteRiskEvaluationUseCase;
import com.coopcredit.credit.application_service.domain.ports.in.GetRiskEvaluationUseCase;
import com.coopcredit.credit.application_service.domain.ports.in.UpdateRiskEvaluationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RiskEvaluationService implements CreateRiskEvaluationUseCase, GetRiskEvaluationUseCase, UpdateRiskEvaluationUseCase,DeleteRiskEvaluationUseCase {

    private final CreateRiskEvaluationUseCase createRiskEvaluationUseCase;
    private final GetRiskEvaluationUseCase getRiskEvaluationUseCase;
    private final UpdateRiskEvaluationUseCase updateRiskEvaluationUseCase;
    private final DeleteRiskEvaluationUseCase deleteRiskEvaluationUseCase;

    @Override
    public RiskEvaluation create(RiskEvaluation newRiskEvaluation) {
        return createRiskEvaluationUseCase.create(newRiskEvaluation);

    }

    @Override
    public boolean delete(Long id) {
        return deleteRiskEvaluationUseCase.delete(id);

    }

    @Override
    public List<RiskEvaluation> getAll() {
        return getRiskEvaluationUseCase.getAll();

    }

    @Override
    public Optional<RiskEvaluation> getById(Long id) {
        return getRiskEvaluationUseCase.getById(id);

    }

    @Override
    public Optional<RiskEvaluation> getByCreditApplicationId(Long creditApplicationId) {
        return getRiskEvaluationUseCase.getByCreditApplicationId(creditApplicationId);
    }

    @Override
    public Optional<RiskEvaluation> update(Long id, RiskEvaluation updateRiskEvaluation) {
        return updateRiskEvaluationUseCase.update(id, updateRiskEvaluation);
    }
}
