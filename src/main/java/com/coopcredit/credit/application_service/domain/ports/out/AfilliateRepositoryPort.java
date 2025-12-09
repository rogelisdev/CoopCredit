package com.coopcredit.credit.application_service.domain.ports.out;

import com.coopcredit.credit.application_service.domain.model.Afilliate;

import java.util.List;
import java.util.Optional;

public interface AfilliateRepositoryPort {
    Afilliate create (Afilliate newAfilliate);
    List<Afilliate> getAll();
    Optional<Afilliate> getById(Long id);
    Optional<Afilliate> update(Afilliate updateAfilliate);
    boolean delete(Long id);
}
