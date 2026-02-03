package com.example.Dingle.noise.repository;

import com.example.Dingle.noise.entity.FloatingPopulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloatingPopulationRepository extends JpaRepository<FloatingPopulation, Long> {
}
