package com.coopcredit.credit.application_service.application.usecase;

import com.coopcredit.credit.application_service.domain.model.CreditApplication;
import com.coopcredit.credit.application_service.domain.ports.in.GetCreditApplicationUseCase;
import com.coopcredit.credit.application_service.domain.ports.out.CreditApplicationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetCreditApplicationUseCaseImpl implements GetCreditApplicationUseCase {

    private final CreditApplicationRepositoryPort repositoryPort;


    @Override
    public List<CreditApplication> getAll() {
        return repositoryPort.getAll();
    }

    @Override
    public Optional<CreditApplication> getById(Long id) {
        return repositoryPort.getById(id);
    }

    @Override
    public List<CreditApplication> getByAfilliateId(Long afilliateId) {
        return repositoryPort.getByAfilliateId(afilliateId);

    }
}
