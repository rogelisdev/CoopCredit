package com.coopcredit.credit.application_service.domain.ports.in;

import com.coopcredit.credit.application_service.domain.model.Afilliate;

import java.util.List;
import java.util.Optional;

public interface GetAfilliateUseCase {
    List<Afilliate> getAll();
    Optional<Afilliate> getById(Long id);
}
