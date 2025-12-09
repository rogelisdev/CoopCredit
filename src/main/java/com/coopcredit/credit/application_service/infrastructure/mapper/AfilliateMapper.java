package com.coopcredit.credit.application_service.infrastructure.mapper;

import com.coopcredit.credit.application_service.domain.model.Afilliate;
import com.coopcredit.credit.application_service.infrastructure.entity.AfilliateEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AfilliateMapper {

    AfilliateEntity toEntity(Afilliate model);

    Afilliate toModel(AfilliateEntity entity);
}