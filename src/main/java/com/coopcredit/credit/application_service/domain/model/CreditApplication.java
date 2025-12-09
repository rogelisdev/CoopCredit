package com.coopcredit.credit.application_service.domain.model;

import com.coopcredit.credit.application_service.domain.model.enums.ApplicationStatus;
import com.coopcredit.credit.application_service.domain.model.enums.RiskLevel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreditApplication {
    private Long id;
    private BigDecimal amount;
    private Integer term;  //Plazo de meses para pagar
    private ApplicationStatus status;
    private LocalDateTime requestedDate;
    private LocalDateTime evaluatedDate;
    private Afilliate afilliate;
    private RiskEvaluation riskEvaluation;

    public CreditApplication(Long id, BigDecimal amount, Integer term, ApplicationStatus status, LocalDateTime requestedDate, LocalDateTime evaluatedDate, Afilliate afilliate, RiskEvaluation riskEvaluation) {
        this.id = id;
        this.amount = amount;
        this.term = term;
        this.status = status;
        this.requestedDate = requestedDate;
        this.evaluatedDate = evaluatedDate;
        this.afilliate = afilliate;
        this.riskEvaluation = riskEvaluation;
    }

    public CreditApplication() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public LocalDateTime getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDateTime requestedDate) {
        this.requestedDate = requestedDate;
    }

    public LocalDateTime getEvaluatedDate() {
        return evaluatedDate;
    }

    public void setEvaluatedDate(LocalDateTime evaluatedDate) {
        this.evaluatedDate = evaluatedDate;
    }

    public Afilliate getAfilliate() {
        return afilliate;
    }

    public void setAfilliate(Afilliate afilliate) {
        this.afilliate = afilliate;
    }

    public RiskEvaluation getRiskEvaluation() {
        return riskEvaluation;
    }

    public void setRiskEvaluation(RiskEvaluation riskEvaluation) {
        this.riskEvaluation = riskEvaluation;
    }
}
