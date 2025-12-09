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
public class CreditApplicationController {

    private final CreditApplicationService creditApplicationService;

    @PostMapping
    public ResponseEntity<CreditApplication> create(@RequestBody CreditApplication creditApplication) {
        CreditApplication created = creditApplicationService.create(creditApplication);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<CreditApplication>> getAll() {
        List<CreditApplication> applications = creditApplicationService.getAll();
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditApplication> getById(@PathVariable Long id) {
        return creditApplicationService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/afilliate/{afilliateId}")
    public ResponseEntity<List<CreditApplication>> getByAfilliateId(@PathVariable Long afilliateId) {
        List<CreditApplication> applications = creditApplicationService.getByAfilliateId(afilliateId);
        return ResponseEntity.ok(applications);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditApplication> update(
            @PathVariable Long id,
            @RequestBody CreditApplication creditApplication) {
        return creditApplicationService.update(id, creditApplication)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = creditApplicationService.delete(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}