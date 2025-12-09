package com.coopcredit.credit.application_service.application.usecase;

import com.coopcredit.credit.application_service.domain.ports.in.DeleteRiskEvaluationUseCase;
import com.coopcredit.credit.application_service.domain.ports.out.RiskEvaluationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteRiskEvaluationUseCaseImpl implements DeleteRiskEvaluationUseCase {

    private final RiskEvaluationRepositoryPort repositoryPort;

    @Override
    public boolean delete(Long id) {
        return repositoryPort.delete(id);
    }
}
