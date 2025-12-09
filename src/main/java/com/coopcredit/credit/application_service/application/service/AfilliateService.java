package com.coopcredit.credit.application_service.application.service;

import com.coopcredit.credit.application_service.domain.model.Afilliate;
import com.coopcredit.credit.application_service.domain.ports.in.CreateAfilliateUseCase;
import com.coopcredit.credit.application_service.domain.ports.in.DeleteAfilliateUseCase;
import com.coopcredit.credit.application_service.domain.ports.in.GetAfilliateUseCase;
import com.coopcredit.credit.application_service.domain.ports.in.UpdateAfilliateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AfilliateService implements CreateAfilliateUseCase, GetAfilliateUseCase, UpdateAfilliateUseCase, DeleteAfilliateUseCase {

    private final CreateAfilliateUseCase createAfilliateUseCase;
    private final GetAfilliateUseCase getAfilliateUseCase;
    private final UpdateAfilliateUseCase updateAfilliateUseCase;
    private final DeleteAfilliateUseCase deleteAfilliateUseCase;

    @Override
    public Afilliate create(Afilliate newAfilliate) {
        return createAfilliateUseCase.create(newAfilliate);
    }

    @Override
    public boolean delete(Long id) {
        return deleteAfilliateUseCase.delete(id);
    }

    @Override
    public List<Afilliate> getAll() {
        return getAfilliateUseCase.getAll();
    }

    @Override
    public Optional<Afilliate> getById(Long id) {
        return getAfilliateUseCase.getById(id);
    }

    @Override
    public Optional<Afilliate> update(Long id, Afilliate updateAfilliate) {
        return updateAfilliateUseCase.update(id, updateAfilliate);
    }
}