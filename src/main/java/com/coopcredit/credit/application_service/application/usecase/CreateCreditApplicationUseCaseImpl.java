package com.coopcredit.credit.application_service.application.usecase;

import com.coopcredit.credit.application_service.domain.model.CreditApplication;
import com.coopcredit.credit.application_service.domain.ports.in.CreateCreditAplicationUseCase;
import com.coopcredit.credit.application_service.domain.ports.out.CreditApplicationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCreditApplicationUseCaseImpl implements CreateCreditAplicationUseCase {

    private final CreditApplicationRepositoryPort repositoryPort;

    @Override
    public CreditApplication create(CreditApplication newCreditApplication) {
        return repositoryPort.create(newCreditApplication);
    }
}
