package com.example.Dingle.noise.repository;

import com.example.Dingle.noise.entity.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireStationRepository extends JpaRepository<FireStation, Long> {
}
