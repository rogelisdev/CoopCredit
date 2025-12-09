package com.coopcredit.credit.application_service.infrastructure.adapter;


import com.coopcredit.credit.application_service.domain.model.RiskEvaluation;
import com.coopcredit.credit.application_service.domain.ports.out.RiskEvaluationRepositoryPort;
import com.coopcredit.credit.application_service.infrastructure.entity.RiskEvaluationEntity;
import com.coopcredit.credit.application_service.infrastructure.mapper.RiskEvaluationMapper;
import com.coopcredit.credit.application_service.infrastructure.repository.RiskEvaluationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RiskEvaluationAdapter implements RiskEvaluationRepositoryPort {

    private final RiskEvaluationJpaRepository jpaRepository;
    private final RiskEvaluationMapper mapper;

    @Override
    public RiskEvaluation create(RiskEvaluation newRiskEvaluation) {
        RiskEvaluationEntity entity = mapper.toEntity(newRiskEvaluation);
        RiskEvaluationEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<RiskEvaluation> getAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RiskEvaluation> getById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<RiskEvaluation> getByCreditApplicationId(Long creditApplicationId) {
        return jpaRepository.findByCreditApplicationId(creditApplicationId)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<RiskEvaluation> update(RiskEvaluation updateRiskEvaluation) {
        if (updateRiskEvaluation.getId() != null && jpaRepository.existsById(updateRiskEvaluation.getId())) {
            RiskEvaluationEntity entity = mapper.toEntity(updateRiskEvaluation);
            RiskEvaluationEntity updated = jpaRepository.save(entity);
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
