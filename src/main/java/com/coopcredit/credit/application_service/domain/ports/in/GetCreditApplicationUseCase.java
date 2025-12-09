package com.coopcredit.credit.application_service.domain.ports.in;

import com.coopcredit.credit.application_service.domain.model.CreditApplication;

import java.util.List;
import java.util.Optional;

public interface GetCreditApplicationUseCase {
    List<CreditApplication> getAll();
    Optional<CreditApplication> getById(Long id);
    List<CreditApplication> getByAfilliateId(Long afilliateId);

}
