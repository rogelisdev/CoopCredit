package com.coopcredit.credit.application_service.infrastructure.controller;

import com.coopcredit.credit.application_service.application.service.AfilliateService;
import com.coopcredit.credit.application_service.domain.model.Afilliate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/afilliates")
@RequiredArgsConstructor
public class AfilliateController {

    private final AfilliateService afilliateService;

    @PostMapping
    public ResponseEntity<Afilliate> create(@RequestBody Afilliate afilliate) {
        Afilliate created = afilliateService.create(afilliate);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Afilliate>> getAll() {
        List<Afilliate> afiliates = afilliateService.getAll();
        return ResponseEntity.ok(afiliates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Afilliate> getById(@PathVariable Long id) {
        return afilliateService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Afilliate> update(
            @PathVariable Long id,
            @RequestBody Afilliate afilliate) {
        return afilliateService.update(id, afilliate)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = afilliateService.delete(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
