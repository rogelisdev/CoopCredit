package com.coopcredit.credit.application_service.application.usecase;

import com.coopcredit.credit.application_service.domain.model.RiskEvaluation;
import com.coopcredit.credit.application_service.domain.ports.in.CreateRiskEvaluationUseCase;
import com.coopcredit.credit.application_service.domain.ports.out.RiskEvaluationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateRiskEvaluationUseCaseImpl implements CreateRiskEvaluationUseCase {

    private final RiskEvaluationRepositoryPort repositoryPort;

    @Override
    public RiskEvaluation create(RiskEvaluation newRiskEvaluation) {
        return repositoryPort.create(newRiskEvaluation);

    }
}
