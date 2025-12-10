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
@io.swagger.v3.oas.annotations.tags.Tag(name = "Risk Evaluations", description = "Operations related to risk evaluations")
public class RiskEvaluationController {

    private final RiskEvaluationService riskEvaluationService;

    @io.swagger.v3.oas.annotations.Operation(summary = "Create a new risk evaluation", description = "Creates a new risk evaluation record")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Risk evaluation created successfully", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RiskEvaluation.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request body", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<RiskEvaluation> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Risk evaluation data", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RiskEvaluation.class))) @RequestBody RiskEvaluation riskEvaluation) {
        RiskEvaluation created = riskEvaluationService.create(riskEvaluation);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Get all risk evaluations", description = "Retrieves a list of all risk evaluations")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of risk evaluations retrieved successfully", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RiskEvaluation.class)))
    })
    @GetMapping
    public ResponseEntity<List<RiskEvaluation>> getAll() {
        List<RiskEvaluation> evaluations = riskEvaluationService.getAll();
        return ResponseEntity.ok(evaluations);
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Get risk evaluation by ID", description = "Retrieves a specific risk evaluation by its ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Risk evaluation found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RiskEvaluation.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Risk evaluation not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<RiskEvaluation> getById(
            @io.swagger.v3.oas.annotations.Parameter(description = "Risk evaluation ID", required = true) @PathVariable Long id) {
        return riskEvaluationService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Get by credit application ID", description = "Retrieves the risk evaluation associated with a specific credit application")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Risk evaluation found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RiskEvaluation.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Risk evaluation not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class)))
    })
    @GetMapping("/credit-application/{creditApplicationId}")
    public ResponseEntity<RiskEvaluation> getByCreditApplicationId(
            @io.swagger.v3.oas.annotations.Parameter(description = "Credit application ID", required = true) @PathVariable Long creditApplicationId) {
        return riskEvaluationService.getByCreditApplicationId(creditApplicationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Update risk evaluation", description = "Updates an existing risk evaluation")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Risk evaluation updated successfully", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RiskEvaluation.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Risk evaluation not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<RiskEvaluation> update(
            @io.swagger.v3.oas.annotations.Parameter(description = "Risk evaluation ID", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated risk evaluation data", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RiskEvaluation.class))) @RequestBody RiskEvaluation riskEvaluation) {
        return riskEvaluationService.update(id, riskEvaluation)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Delete risk evaluation", description = "Deletes a risk evaluation from the system")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Risk evaluation deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Risk evaluation not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @io.swagger.v3.oas.annotations.Parameter(description = "Risk evaluation ID", required = true) @PathVariable Long id) {
        boolean deleted = riskEvaluationService.delete(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}