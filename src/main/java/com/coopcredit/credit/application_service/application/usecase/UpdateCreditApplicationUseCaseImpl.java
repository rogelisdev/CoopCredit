package com.coopcredit.credit.application_service.application.usecase;

import com.coopcredit.credit.application_service.domain.model.CreditApplication;
import com.coopcredit.credit.application_service.domain.ports.in.UpdateCreditApplicationUseCase;
import com.coopcredit.credit.application_service.domain.ports.out.CreditApplicationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdateCreditApplicationUseCaseImpl implements UpdateCreditApplicationUseCase {

    private final CreditApplicationRepositoryPort repositoryPort;

    @Override
    public Optional<CreditApplication> update(Long id, CreditApplication updateCreditApplication) {
        return repositoryPort.update(updateCreditApplication);
    }
}
