package com.coopcredit.credit.application_service.infrastructure.entity;

import com.coopcredit.credit.application_service.domain.model.enums.RiskLevel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "risk_evaluation")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RiskEvaluationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "risk_evaluation_id")
    private Long id;

    @Column(nullable = false)
    private Integer score;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RiskLevel level;

    @Column(length = 500)
    private String recommendations;

    @Column(name = "evaluated_at", nullable = false)
    private LocalDateTime evaluatedAt;

    // Inverse OneToOne relationship with CreditApplication
    @OneToOne(mappedBy = "riskEvaluation", fetch = FetchType.LAZY)
    private CreditApplicationEntity creditApplication;
}
