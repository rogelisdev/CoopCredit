package com.coopcredit.credit.application_service.infrastructure.repository;

import com.coopcredit.credit.application_service.infrastructure.entity.CreditApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaCreditApplicationRepository extends JpaRepository<CreditApplicationEntity, Long> {
    List<CreditApplicationEntity> findByAfilliateId(Long afilliateId);
}