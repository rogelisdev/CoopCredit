package com.coopcredit.credit.application_service.domain.ports.in;

import com.coopcredit.credit.application_service.domain.model.CreditApplication;

import java.util.Optional;

public interface UpdateCreditApplicationUseCase {
    Optional<CreditApplication> update(Long id, CreditApplication updateCreditApplication);

}
