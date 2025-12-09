package com.coopcredit.credit.application_service.domain.ports.in;

import com.coopcredit.credit.application_service.domain.model.Afilliate;

import java.util.Optional;

public interface UpdateAfilliateUseCase {
    Optional<Afilliate> update(Long id, Afilliate updateAfilliate);
}
