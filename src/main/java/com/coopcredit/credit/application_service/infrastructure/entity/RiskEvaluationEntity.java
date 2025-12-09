package com.coopcredit.credit.application_service.infrastructure.entity;

import com.coopcredit.credit.application_service.domain.model.enums.RiskLevel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "risk_evaluation")
public class RiskEvaluationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;

    @Enumerated(EnumType.STRING)
    private RiskLevel level;

    @Column(length = 500)
    private String recommendations;

    private LocalDateTime evaluatedAt;

    // Relación OneToOne inversa con CreditApplication
    @OneToOne(mappedBy = "riskEvaluation")
    private CreditApplicationEntity creditApplication;
}
