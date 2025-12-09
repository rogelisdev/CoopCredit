package com.coopcredit.credit.application_service.domain.model;

import com.coopcredit.credit.application_service.domain.model.enums.RiskLevel;

import java.time.LocalDateTime;

public class RiskEvaluation {
    private Long id;
    private Integer score;
    private RiskLevel level;
    private String recommendations;
    private LocalDateTime evaluatedAt;

    public RiskEvaluation(Long id, Integer score, RiskLevel level, String recommendations, LocalDateTime evaluatedAt) {
        this.id = id;
        this.score = score;
        this.level = level;
        this.recommendations = recommendations;
        this.evaluatedAt = evaluatedAt;
    }

    public RiskEvaluation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public RiskLevel getLevel() {
        return level;
    }

    public void setLevel(RiskLevel level) {
        this.level = level;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public LocalDateTime getEvaluatedAt() {
        return evaluatedAt;
    }

    public void setEvaluatedAt(LocalDateTime evaluatedAt) {
        this.evaluatedAt = evaluatedAt;
    }
}
