package com.coopcredit.credit.application_service.application.usecase;

import com.coopcredit.credit.application_service.domain.model.RiskEvaluation;
import com.coopcredit.credit.application_service.domain.ports.in.UpdateRiskEvaluationUseCase;
import com.coopcredit.credit.application_service.domain.ports.out.RiskEvaluationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdateRiskEvaluationUseCaseImpl implements UpdateRiskEvaluationUseCase {

    private final RiskEvaluationRepositoryPort repositoryPort;

    @Override
    public Optional<RiskEvaluation> update(Long id, RiskEvaluation updateRiskEvaluation) {
        return repositoryPort.update(updateRiskEvaluation);
    }
}
