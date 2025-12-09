package com.coopcredit.credit.application_service.application.usecase;

import com.coopcredit.credit.application_service.domain.model.Afilliate;
import com.coopcredit.credit.application_service.domain.ports.in.CreateAfilliateUseCase;
import com.coopcredit.credit.application_service.domain.ports.out.AfilliateRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateAfilliateUseCaseImpl implements CreateAfilliateUseCase {

    private final AfilliateRepositoryPort repositoryPort;

    @Override
    public Afilliate create(Afilliate newAfilliate) {
        return repositoryPort.create(newAfilliate);
    }
}
