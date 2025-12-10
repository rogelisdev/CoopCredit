package com.coopcredit.credit.application_service.infrastructure.mapper;

import com.coopcredit.credit.application_service.domain.model.CreditApplication;
import com.coopcredit.credit.application_service.infrastructure.entity.AfilliateEntity;
import com.coopcredit.credit.application_service.infrastructure.entity.CreditApplicationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditApplicationMapper {

    private final RiskEvaluationMapper riskEvaluationMapper;

    public CreditApplication toDomain(CreditApplicationEntity entity) {
        if (entity == null) {
            return null;
        }

        return CreditApplication.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .term(entity.getTerm())
                .status(entity.getStatus())
                .requestedDate(entity.getRequestedDate())
                .evaluatedDate(entity.getEvaluatedDate())
                .afilliateId(entity.getAffiliate() != null ? entity.getAffiliate().getId() : null)
                .riskEvaluation(riskEvaluationMapper.toDomain(entity.getRiskEvaluation()))
                .build();
    }

    public CreditApplicationEntity toEntity(CreditApplication domain) {
        if (domain == null) {
            return null;
        }

        CreditApplicationEntity entity = CreditApplicationEntity.builder()
                .id(domain.getId())
                .amount(domain.getAmount())
                .term(domain.getTerm())
                .status(domain.getStatus())
                .requestedDate(domain.getRequestedDate())
                .evaluatedDate(domain.getEvaluatedDate())
                .riskEvaluation(riskEvaluationMapper.toEntity(domain.getRiskEvaluation()))
                .build();

        if (domain.getAfilliateId() != null) {
            AfilliateEntity afilliateEntity = new AfilliateEntity();
            afilliateEntity.setId(domain.getAfilliateId());
            entity.setAffiliate(afilliateEntity);
        }

        return entity;
    }
}
