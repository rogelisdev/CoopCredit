package com.coopcredit.credit.application_service.infrastructure.entity;

import com.coopcredit.credit.application_service.domain.model.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_application", indexes = {
        @Index(name = "idx_credit_app_status", columnList = "status"),
        @Index(name = "idx_credit_app_affiliate", columnList = "affiliate_id")
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_application_id")
    private Long id;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private Integer term; // Term in months

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ApplicationStatus status;

    @Column(name = "requested_date", nullable = false)
    private LocalDateTime requestedDate;

    @Column(name = "evaluated_date")
    private LocalDateTime evaluatedDate;

    // Relationship: CreditApplication *..1 Affiliate (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "affiliate_id", nullable = false)
    private AfilliateEntity affiliate;

    // Relationship: CreditApplication 1..1 RiskEvaluation (OneToOne)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "risk_evaluation_id", referencedColumnName = "risk_evaluation_id")
    private RiskEvaluationEntity riskEvaluation;
}
