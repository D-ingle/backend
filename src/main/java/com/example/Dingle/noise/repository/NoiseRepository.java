package com.example.Dingle.noise.repository;

import com.example.Dingle.noise.entity.Noise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoiseRepository extends JpaRepository<Noise, Long> {
}
