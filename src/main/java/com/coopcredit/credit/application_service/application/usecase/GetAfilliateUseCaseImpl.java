package com.coopcredit.credit.application_service.application.usecase;

import com.coopcredit.credit.application_service.domain.model.Afilliate;
import com.coopcredit.credit.application_service.domain.ports.in.GetAfilliateUseCase;
import com.coopcredit.credit.application_service.domain.ports.out.AfilliateRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAfilliateUseCaseImpl implements GetAfilliateUseCase {

    private final AfilliateRepositoryPort repositoryPort;

    @Override
    public List<Afilliate> getAll() {
        return repositoryPort.getAll();
    }

    @Override
    public Optional<Afilliate> getById(Long id) {
        return repositoryPort.getById(id);
    }
}
