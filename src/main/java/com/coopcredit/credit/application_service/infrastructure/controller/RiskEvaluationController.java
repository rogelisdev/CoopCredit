package com.coopcredit.credit.application_service.infrastructure.controller;

import com.coopcredit.credit.application_service.application.service.RiskEvaluationService;
import com.coopcredit.credit.application_service.domain.model.RiskEvaluation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risk-evaluations")
@RequiredArgsConstructor
public class RiskEvaluationController {

    private final RiskEvaluationService riskEvaluationService;

    @PostMapping
    public ResponseEntity<RiskEvaluation> create(@RequestBody RiskEvaluation riskEvaluation) {
        RiskEvaluation created = riskEvaluationService.create(riskEvaluation);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<RiskEvaluation>> getAll() {
        List<RiskEvaluation> evaluations = riskEvaluationService.getAll();
        return ResponseEntity.ok(evaluations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RiskEvaluation> getById(@PathVariable Long id) {
        return riskEvaluationService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/credit-application/{creditApplicationId}")
    public ResponseEntity<RiskEvaluation> getByCreditApplicationId(@PathVariable Long creditApplicationId) {
        return riskEvaluationService.getByCreditApplicationId(creditApplicationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RiskEvaluation> update(
            @PathVariable Long id,
            @RequestBody RiskEvaluation riskEvaluation) {
        return riskEvaluationService.update(id, riskEvaluation)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = riskEvaluationService.delete(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}