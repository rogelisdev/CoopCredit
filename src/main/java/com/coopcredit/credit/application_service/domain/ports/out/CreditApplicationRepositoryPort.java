package com.coopcredit.credit.application_service.domain.ports.out;

import com.coopcredit.credit.application_service.domain.model.CreditApplication;

import java.util.List;
import java.util.Optional;

public interface CreditApplicationRepositoryPort {
    CreditApplication create(CreditApplication newCreditApplication);
    List<CreditApplication> getAll();
    Optional<CreditApplication> getById(Long id);
    List<CreditApplication> getByAfilliateId(Long afilliateId);
    Optional<CreditApplication> update(CreditApplication updateCreditApplication);
    boolean delete(Long id);
}
