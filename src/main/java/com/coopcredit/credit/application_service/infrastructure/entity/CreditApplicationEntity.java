package com.coopcredit.credit.application_service.infrastructure.entity;

import com.coopcredit.credit.application_service.domain.model.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "credit_application")
public class CreditApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private Integer term;  // Plazo en meses

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private LocalDateTime requestedDate;

    private LocalDateTime evaluatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "afilliate_id")
    private AfilliateEntity afilliate;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "risk_evaluation_id", referencedColumnName = "id")
    private RiskEvaluationEntity riskEvaluation;
}
