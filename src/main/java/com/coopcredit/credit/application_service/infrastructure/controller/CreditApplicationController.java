package com.coopcredit.credit.application_service.infrastructure.controller;

import com.coopcredit.credit.application_service.application.service.CreditApplicationService;
import com.coopcredit.credit.application_service.domain.model.CreditApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit-applications")
@RequiredArgsConstructor
@io.swagger.v3.oas.annotations.tags.Tag(name = "Credit Applications", description = "Operations related to credit applications")
public class CreditApplicationController {

    private final CreditApplicationService creditApplicationService;

    @io.swagger.v3.oas.annotations.Operation(summary = "Create a new credit application", description = "Creates a new credit application record")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Credit application created successfully", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CreditApplication.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request body", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<CreditApplication> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credit application data", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CreditApplication.class))) @RequestBody CreditApplication creditApplication) {
        CreditApplication created = creditApplicationService.create(creditApplication);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Get all credit applications", description = "Retrieves a list of all credit applications")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of credit applications retrieved successfully", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CreditApplication.class)))
    })
    @GetMapping
    public ResponseEntity<List<CreditApplication>> getAll() {
        List<CreditApplication> applications = creditApplicationService.getAll();
        return ResponseEntity.ok(applications);
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Get credit application by ID", description = "Retrieves a specific credit application by its ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Credit application found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CreditApplication.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Credit application not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CreditApplication> getById(
            @io.swagger.v3.oas.annotations.Parameter(description = "Credit application ID", required = true) @PathVariable Long id) {
        return creditApplicationService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Get credit applications by affiliate ID", description = "Retrieves all credit applications associated with a specific affiliate")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of credit applications retrieved successfully", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CreditApplication.class)))
    })
    @GetMapping("/afilliate/{afilliateId}")
    public ResponseEntity<List<CreditApplication>> getByAfilliateId(
            @io.swagger.v3.oas.annotations.Parameter(description = "Affiliate ID", required = true) @PathVariable Long afilliateId) {
        List<CreditApplication> applications = creditApplicationService.getByAfilliateId(afilliateId);
        return ResponseEntity.ok(applications);
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Update credit application", description = "Updates an existing credit application")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Credit application updated successfully", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CreditApplication.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Credit application not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<CreditApplication> update(
            @io.swagger.v3.oas.annotations.Parameter(description = "Credit application ID", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated credit application data", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CreditApplication.class))) @RequestBody CreditApplication creditApplication) {
        return creditApplicationService.update(id, creditApplication)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Delete credit application", description = "Deletes a credit application from the system")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Credit application deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Credit application not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @io.swagger.v3.oas.annotations.Parameter(description = "Credit application ID", required = true) @PathVariable Long id) {
        boolean deleted = creditApplicationService.delete(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}