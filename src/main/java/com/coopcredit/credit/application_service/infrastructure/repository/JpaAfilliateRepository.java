package com.coopcredit.credit.application_service.infrastructure.repository;

import com.coopcredit.credit.application_service.infrastructure.entity.AfilliateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaAfilliateRepository extends JpaRepository<AfilliateEntity, Long> {
    Optional<AfilliateEntity> findByDocument(String document);
}
