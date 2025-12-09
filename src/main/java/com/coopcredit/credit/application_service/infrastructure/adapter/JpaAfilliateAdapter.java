package com.coopcredit.credit.application_service.infrastructure.adapter;

import com.coopcredit.credit.application_service.domain.model.Afilliate;
import com.coopcredit.credit.application_service.domain.ports.out.AfilliateRepositoryPort;
import com.coopcredit.credit.application_service.infrastructure.entity.AfilliateEntity;
import com.coopcredit.credit.application_service.infrastructure.mapper.AfilliateMapper;
import com.coopcredit.credit.application_service.infrastructure.repository.JpaAfilliateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaAfilliateAdapter implements AfilliateRepositoryPort {

    private final JpaAfilliateRepository jpaAfilliateRepository;
    private final AfilliateMapper afilliateMapper;


    @Override
    public Afilliate create(Afilliate newAfilliate) {
        AfilliateEntity saved = jpaAfilliateRepository.save(afilliateMapper.toEntity(newAfilliate));
        return afilliateMapper.toModel(saved);
    }

    @Override
    public List<Afilliate> getAll() {
        return jpaAfilliateRepository.findAll()
                .stream()
                .map(afilliateMapper::toModel)
                .toList();
    }

    @Override
    public Optional<Afilliate> getById(Long id) {
        return jpaAfilliateRepository.findById(id).map(afilliateMapper::toModel);
    }

    @Override
    public Optional<Afilliate> update(Afilliate updateAfilliate) {
        AfilliateEntity updated = jpaAfilliateRepository.save(afilliateMapper.toEntity(updateAfilliate));
        return Optional.ofNullable(afilliateMapper.toModel(updated));
    }

    @Override
    public boolean delete(Long id) {
        if(!jpaAfilliateRepository.existsById(id)) return false;
        jpaAfilliateRepository.deleteById(id);
        return true;
    }
}
