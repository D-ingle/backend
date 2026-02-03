package com.example.Dingle.environment.repository;

import com.example.Dingle.environment.entity.Noise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoiseRepository extends JpaRepository<Noise, Long> {
}
