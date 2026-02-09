package com.example.Dingle.safety.repository;

import com.example.Dingle.safety.entity.PropertyPathLight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyPathLightRepository extends JpaRepository<PropertyPathLight, Long> {
}
