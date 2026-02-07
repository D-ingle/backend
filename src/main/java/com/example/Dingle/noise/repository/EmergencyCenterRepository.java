package com.example.Dingle.noise.repository;

import com.example.Dingle.noise.entity.EmergencyCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyCenterRepository extends JpaRepository<EmergencyCenter, Long> {
}
