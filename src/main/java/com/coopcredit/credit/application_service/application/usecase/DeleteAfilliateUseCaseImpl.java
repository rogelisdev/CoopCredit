package com.coopcredit.credit.application_service.application.usecase;

import com.coopcredit.credit.application_service.domain.ports.in.DeleteAfilliateUseCase;
import com.coopcredit.credit.application_service.domain.ports.out.AfilliateRepositoryPort;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteAfilliateUseCaseImpl implements DeleteAfilliateUseCase {

    private final AfilliateRepositoryPort repositoryPort;

    @Override
    public boolean delete(Long id) {
        return repositoryPort.delete(id);
    }
}
