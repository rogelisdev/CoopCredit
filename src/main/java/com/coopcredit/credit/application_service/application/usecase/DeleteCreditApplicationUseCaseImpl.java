package com.coopcredit.credit.application_service.application.usecase;

import com.coopcredit.credit.application_service.domain.ports.in.DeleteCreditApplicationUseCase;
import com.coopcredit.credit.application_service.domain.ports.out.CreditApplicationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCreditApplicationUseCaseImpl implements DeleteCreditApplicationUseCase {

    private final CreditApplicationRepositoryPort repositoryPort;

    @Override
    public boolean delete(Long id) {
        return repositoryPort.delete(id);
    }
}
