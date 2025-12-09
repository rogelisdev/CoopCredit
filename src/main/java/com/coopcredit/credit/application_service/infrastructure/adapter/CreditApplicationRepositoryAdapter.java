package com.coopcredit.credit.application_service.infrastructure.adapter;

import com.coopcredit.credit.application_service.domain.model.CreditApplication;
import com.coopcredit.credit.application_service.domain.ports.out.CreditApplicationRepositoryPort;
import com.coopcredit.credit.application_service.infrastructure.entity.CreditApplicationEntity;
import com.coopcredit.credit.application_service.infrastructure.mapper.CreditApplicationMapper;
import com.coopcredit.credit.application_service.infrastructure.repository.JpaCreditApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreditApplicationRepositoryAdapter implements CreditApplicationRepositoryPort {

    private final JpaCreditApplicationRepository jpaRepository;
    private final CreditApplicationMapper mapper;

    @Override
    public CreditApplication create(CreditApplication newCreditApplication) {
        CreditApplicationEntity entity = mapper.toEntity(newCreditApplication);
        CreditApplicationEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<CreditApplication> getAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CreditApplication> getById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<CreditApplication> getByAfilliateId(Long afilliateId) {
        return jpaRepository.findByAfilliateId(afilliateId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CreditApplication> update(CreditApplication updateCreditApplication) {
        if (updateCreditApplication.getId() != null && jpaRepository.existsById(updateCreditApplication.getId())) {
            CreditApplicationEntity entity = mapper.toEntity(updateCreditApplication);
            CreditApplicationEntity updated = jpaRepository.save(entity);
            return Optional.of(mapper.toDomain(updated));
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        if (jpaRepository.existsById(id)) {
            jpaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
