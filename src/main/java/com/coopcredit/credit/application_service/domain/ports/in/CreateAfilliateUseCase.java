package com.coopcredit.credit.application_service.domain.ports.in;

import com.coopcredit.credit.application_service.domain.model.Afilliate;

public interface CreateAfilliateUseCase {
    Afilliate create(Afilliate newAfilliate);
}
