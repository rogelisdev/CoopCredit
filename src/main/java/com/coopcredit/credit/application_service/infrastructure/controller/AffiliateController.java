package com.coopcredit.credit.application_service.infrastructure.controller;

import com.coopcredit.credit.application_service.application.service.AfilliateService;
import com.coopcredit.credit.application_service.domain.model.Afilliate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/affiliates")
@RequiredArgsConstructor
@io.swagger.v3.oas.annotations.tags.Tag(name = "Affiliates", description = "Operations related to affiliates")
public class AffiliateController {

        private final AfilliateService afilliateService;

        @io.swagger.v3.oas.annotations.Operation(summary = "Create a new affiliate", description = "Creates a new affiliate in the system")
        @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Affiliate created successfully", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Afilliate.class))),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request body", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class)))
        })
        @PostMapping
        public ResponseEntity<Afilliate> create(
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Affiliate data", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Afilliate.class))) @RequestBody Afilliate afilliate) {
                Afilliate created = afilliateService.create(afilliate);
                return ResponseEntity.status(HttpStatus.CREATED).body(created);
        }

        @io.swagger.v3.oas.annotations.Operation(summary = "Get all affiliates", description = "Retrieves a list of all registered affiliates")
        @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of affiliates retrieved successfully", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Afilliate.class)))
        })
        @GetMapping
        public ResponseEntity<List<Afilliate>> getAll() {
                List<Afilliate> afiliates = afilliateService.getAll();
                return ResponseEntity.ok(afiliates);
        }

        @io.swagger.v3.oas.annotations.Operation(summary = "Get affiliate by ID", description = "Retrieves a specific affiliate by their ID")
        @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Affiliate found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Afilliate.class))),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Affiliate not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class)))
        })
        @GetMapping("/{id}")
        public ResponseEntity<Afilliate> getById(
                        @io.swagger.v3.oas.annotations.Parameter(description = "Affiliate ID", required = true) @PathVariable Long id) {
                return afilliateService.getById(id)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        @io.swagger.v3.oas.annotations.Operation(summary = "Update affiliate", description = "Updates an existing affiliate's information")
        @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Affiliate updated successfully", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Afilliate.class))),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Affiliate not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class)))
        })
        @PutMapping("/{id}")
        public ResponseEntity<Afilliate> update(
                        @io.swagger.v3.oas.annotations.Parameter(description = "Affiliate ID", required = true) @PathVariable Long id,
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated affiliate data", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Afilliate.class))) @RequestBody Afilliate afilliate) {
                return afilliateService.update(id, afilliate)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        @io.swagger.v3.oas.annotations.Operation(summary = "Delete affiliate", description = "Deletes an affiliate from the system")
        @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Affiliate deleted successfully"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Affiliate not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.coopcredit.credit.application_service.infrastructure.adapter.exception.ErrorResponse.class)))
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(
                        @io.swagger.v3.oas.annotations.Parameter(description = "Affiliate ID", required = true) @PathVariable Long id) {
                boolean deleted = afilliateService.delete(id);
                return deleted
                                ? ResponseEntity.noContent().build()
                                : ResponseEntity.notFound().build();
        }
}
