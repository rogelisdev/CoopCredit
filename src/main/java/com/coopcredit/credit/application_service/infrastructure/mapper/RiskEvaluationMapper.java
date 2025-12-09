package com.coopcredit.credit.application_service.infrastructure.mapper;

import com.coopcredit.credit.application_service.domain.model.RiskEvaluation;
import com.coopcredit.credit.application_service.infrastructure.entity.CreditApplicationEntity;
import com.coopcredit.credit.application_service.infrastructure.entity.RiskEvaluationEntity;
import org.springframework.stereotype.Component;

@Component
public class RiskEvaluationMapper {

    public RiskEvaluation toDomain(RiskEvaluationEntity entity) {
        if (entity == null) {
            return null;
        }

        return RiskEvaluation.builder()
                .id(entity.getId())
                .score(entity.getScore())
                .level(entity.getLevel())
                .recommendations(entity.getRecommendations())
                .evaluatedAt(entity.getEvaluatedAt())
                .creditApplicationId(entity.getCreditApplication() != null ?
                        entity.getCreditApplication().getId() : null)
                .build();
    }

    public RiskEvaluationEntity toEntity(RiskEvaluation domain) {
        if (domain == null) {
            return null;
        }

        RiskEvaluationEntity entity = RiskEvaluationEntity.builder()
                .id(domain.getId())
                .score(domain.getScore())
                .level(domain.getLevel())
                .recommendations(domain.getRecommendations())
                .evaluatedAt(domain.getEvaluatedAt())
                .build();

        if (domain.getCreditApplicationId() != null) {
            CreditApplicationEntity creditApplicationEntity = new CreditApplicationEntity();
            creditApplicationEntity.setId(domain.getCreditApplicationId());
            entity.setCreditApplication(creditApplicationEntity);
        }

        return entity;
    }
}