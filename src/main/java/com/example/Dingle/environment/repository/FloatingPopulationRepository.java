package com.example.Dingle.environment.repository;

import com.example.Dingle.environment.entity.FloatingPopulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloatingPopulationRepository extends JpaRepository<FloatingPopulation, Long> {
}
