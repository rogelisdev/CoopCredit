package com.coopcredit.credit.application_service.domain.model;

import com.coopcredit.credit.application_service.domain.model.enums.RiskLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RiskEvaluation {
    private Long id;
    private Integer score;
    private RiskLevel level;
    private String recommendations;
    private LocalDateTime evaluatedAt;

    // Use ID instead of full object to avoid circular dependency
    private Long creditApplicationId;
}
