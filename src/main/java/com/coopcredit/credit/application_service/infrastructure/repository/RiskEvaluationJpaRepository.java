package com.coopcredit.credit.application_service.infrastructure.repository;

import com.coopcredit.credit.application_service.infrastructure.entity.RiskEvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiskEvaluationJpaRepository extends JpaRepository<RiskEvaluationEntity, Long> {
    Optional<RiskEvaluationEntity> findByCreditApplicationId(Long creditApplicationId);
}