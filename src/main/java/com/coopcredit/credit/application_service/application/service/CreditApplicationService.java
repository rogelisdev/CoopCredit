package com.coopcredit.credit.application_service.application.service;

import com.coopcredit.credit.application_service.domain.model.CreditApplication;
import com.coopcredit.credit.application_service.domain.ports.in.CreateCreditAplicationUseCase;
import com.coopcredit.credit.application_service.domain.ports.in.DeleteCreditApplicationUseCase;
import com.coopcredit.credit.application_service.domain.ports.in.GetCreditApplicationUseCase;
import com.coopcredit.credit.application_service.domain.ports.in.UpdateCreditApplicationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreditApplicationService implements CreateCreditAplicationUseCase, GetCreditApplicationUseCase, UpdateCreditApplicationUseCase, DeleteCreditApplicationUseCase{

    private final CreateCreditAplicationUseCase createCreditApplicationUseCase;
    private final GetCreditApplicationUseCase getCreditApplicationUseCase;
    private final UpdateCreditApplicationUseCase updateCreditApplicationUseCase;
    private final DeleteCreditApplicationUseCase deleteCreditApplicationUseCase;



    @Override
    public CreditApplication create(CreditApplication newCreditApplication) {
        return createCreditApplicationUseCase.create(newCreditApplication);

    }

    @Override
    public boolean delete(Long id) {
        return deleteCreditApplicationUseCase.delete(id);
    }

    @Override
    public List<CreditApplication> getAll() {
        return getCreditApplicationUseCase.getAll();

    }

    @Override
    public Optional<CreditApplication> getById(Long id) {
        return getCreditApplicationUseCase.getById(id);

    }

    @Override
    public List<CreditApplication> getByAfilliateId(Long afilliateId) {
        return getCreditApplicationUseCase.getByAfilliateId(afilliateId);
    }

    @Override
    public Optional<CreditApplication> update(Long id, CreditApplication updateCreditApplication) {
        return updateCreditApplicationUseCase.update(id, updateCreditApplication);
    }
}
