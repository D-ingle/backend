package com.example.Dingle.property.repository;

import com.example.Dingle.safety.entity.PropertyPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyPathRepository extends JpaRepository<PropertyPath, Long> {
}
