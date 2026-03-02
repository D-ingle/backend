package com.example.Dingle.infra.service;

import com.example.Dingle.infra.type.InfraType;
import com.example.Dingle.safety.entity.Safety;
import com.example.Dingle.safety.repository.SafetyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CctvDbWriter {

    private final SafetyRepository safetyRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void deleteExisting(Long districtId) {
        safetyRepository.deleteByDistrictIdAndInfraType(districtId, InfraType.CCTV);
        em.flush();
        em.clear();
    }

    @Transactional
    public void saveChunk(List<Safety> batch) {
        safetyRepository.saveAll(batch);
        em.flush();
        em.clear();
    }
}
