package com.coopcredit.credit.application_service.application.usecase;

import com.coopcredit.credit.application_service.domain.model.RiskEvaluation;
import com.coopcredit.credit.application_service.domain.ports.in.GetRiskEvaluationUseCase;
import com.coopcredit.credit.application_service.domain.ports.out.RiskEvaluationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetRiskEvaluationUseCaseImpl implements GetRiskEvaluationUseCase {

    private final RiskEvaluationRepositoryPort repositoryPort;

    @Override
    public List<RiskEvaluation> getAll() {
        return repositoryPort.getAll();
    }

    @Override
    public Optional<RiskEvaluation> getById(Long id) {
        return repositoryPort.getById(id);

    }

    @Override
    public Optional<RiskEvaluation> getByCreditApplicationId(Long creditApplicationId) {
        return repositoryPort.getByCreditApplicationId(creditApplicationId);

    }
}
