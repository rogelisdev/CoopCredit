package com.coopcredit.credit.application_service.infrastructure.repository;

import com.coopcredit.credit.application_service.infrastructure.entity.TokenEntity;
import com.coopcredit.credit.application_service.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository  extends JpaRepository<TokenEntity, Long> {
    List<TokenEntity> findAllByUserAndExpiredFalseAndRevokedFalse(UserEntity user);
}
