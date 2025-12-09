package com.coopcredit.credit.application_service.application.usecase;

import com.coopcredit.credit.application_service.domain.model.Afilliate;
import com.coopcredit.credit.application_service.domain.ports.in.UpdateAfilliateUseCase;
import com.coopcredit.credit.application_service.domain.ports.out.AfilliateRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UpdateAfilliateUseCaseImpl implements UpdateAfilliateUseCase {

    private final AfilliateRepositoryPort repositoryPort;

    @Override
    public Optional<Afilliate> update(Long id, Afilliate updateAfilliate) {
        return repositoryPort.update(updateAfilliate);
    }
}
