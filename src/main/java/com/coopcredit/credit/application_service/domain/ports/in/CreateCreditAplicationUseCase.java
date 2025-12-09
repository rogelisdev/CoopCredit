package com.coopcredit.credit.application_service.domain.ports.in;

import com.coopcredit.credit.application_service.domain.model.CreditApplication;

public interface CreateCreditAplicationUseCase {
    CreditApplication create(CreditApplication newCreditApplication);

}
